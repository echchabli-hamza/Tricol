package com.tricol.tricol.service.impl;

import com.tricol.tricol.dto.CommandeFournisseurDTO;
import com.tricol.tricol.dto.CommandeFournisseurInputDTO;
import com.tricol.tricol.dto.ProduitQuantiteDTO;
import com.tricol.tricol.entity.*;
import com.tricol.tricol.mapper.CommandeFournisseurMapper;
import com.tricol.tricol.repository.*;
import com.tricol.tricol.service.CommandeFournisseurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final ProduitRepository produitRepository;
    private final CommandeFournisseurMapper commandeFournisseurMapper;
    private final FournisseurRepository fournisseurRepository;
    private  final MouvementStockRepository mouvementStockRepository ;

    private final ProductCostRepository productCostRepository;

    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          ProduitRepository produitRepository,
                                          CommandeFournisseurMapper commandeFournisseurMapper, FournisseurRepository fournisseurRepository, MouvementStockRepository mouvementStockRepository, ProductCostRepository productCostRepository) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.produitRepository = produitRepository;
        this.commandeFournisseurMapper = commandeFournisseurMapper;

        this.fournisseurRepository = fournisseurRepository;
        this.mouvementStockRepository = mouvementStockRepository;
        this.productCostRepository = productCostRepository;
    }

    @Override
    public CommandeFournisseurDTO save(CommandeFournisseurInputDTO inputDTO) {


        List<Long> requestedIds = inputDTO.getProduits()
                .stream()
                .map(ProduitQuantiteDTO::getProduitId)
                .toList();


        List<Produit> produits = produitRepository.findAllById(requestedIds);


        Set<Long> foundIds = produits.stream()
                .map(Produit::getId)
                .collect(Collectors.toSet());

        List<Long> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new RuntimeException("Produits " + missingIds + " n'existent pas");
        }


        Fournisseur fournisseur = fournisseurRepository.findById(inputDTO.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur n'existe pas"));


        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setDateCommande(inputDTO.getDateCommande());
        commande.setFournisseur(fournisseur);
        commande.setProduits(produits);
        commande = commandeFournisseurRepository.save(commande);
        double total = 0.0;

        for (ProduitQuantiteDTO pq : inputDTO.getProduits()) {

            Produit produit = produits.stream()
                    .filter(p -> p.getId().equals(pq.getProduitId()))
                    .findFirst()
                    .orElse(null);

            if (produit != null && pq.getQuantite() != null && pq.getQuantite() > 0) {

                List<ProductCost> batches = productCostRepository
                        .findByProduitIdAndRemainingUnitsGreaterThanOrderByIdAsc(produit.getId(), 0);

                int restant = pq.getQuantite();

                for (ProductCost batch : batches) {
                    if (restant <= 0) break;

                    int available = batch.getRemainingUnits();
                    int used = Math.min(available, restant);
                    if (used<0) continue;

                    total += used*batch.getPrixUnitaire();

                    restant -= used;
                }



                if (restant > 0) {
                    throw new RuntimeException("Stock insuffisant pour le produit " + produit.getNom());
                }

                MouvementStock mouvement = new MouvementStock();
                mouvement.setProduit(produit);
                mouvement.setType(null);
                mouvement.setQuantity(pq.getQuantite());
                mouvement.setCommandeFournisseur(commande);
                mouvement.setDatemovements(new Date(System.currentTimeMillis()));
                mouvementStockRepository.save(mouvement);
            }

        }
        commande.setStatut("EN_ATTENTE");
        //commande.setMontantTotal(total);
        return commandeFournisseurMapper.toDto(commande);
    }

    @Override
    public CommandeFournisseurDTO findById(Long id) {
        return commandeFournisseurRepository.findById(id)
                .map(commandeFournisseurMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Commande fournisseur introuvable avec id " + id));
    }

    @Override
    public List<CommandeFournisseurDTO> findAll() {
        return commandeFournisseurRepository.findAll()
                .stream()
                .map(commandeFournisseurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommandeFournisseurDTO deliverCommande(Long commandeId) {

        // 1. Fetch the commande
        CommandeFournisseur commande = commandeFournisseurRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (!"EN_ATTENTE".equals(commande.getStatut())) {
            throw new RuntimeException("Commande déjà traitée ou annulée");
        }

        // 2. Retrieve all mouvements for this commande
        List<MouvementStock> mouvements = mouvementStockRepository.findByCommandeFournisseurId(commandeId);

        double total = 0.0;

        for (MouvementStock mouvement : mouvements) {

            mouvement.setType(TypeMouvement.SORTIE);
            Produit produit = mouvement.getProduit();
            int restant = mouvement.getQuantity();

            // 3. Fetch batches with remaining stock
            List<ProductCost> batches = productCostRepository
                    .findByProduitIdAndRemainingUnitsGreaterThanOrderByIdAsc(produit.getId(), 0);

            for (ProductCost batch : batches) {
                if (restant <= 0) break;

                int available = batch.getRemainingUnits();
                int used = Math.min(available, restant);

                batch.setRemainingUnits(available - used);
                productCostRepository.save(batch);

                total += used * batch.getPrixUnitaire();

                restant -= used;
            }

            if (restant > 0) {
                throw new RuntimeException("Stock insuffisant pour le produit " + produit.getNom());
            }
        }

        // 4. Update commande status to LIVRÉE and total
        commande.setStatut("LIVRÉE");
        commande.setMontantTotal(total);
        commandeFournisseurRepository.save(commande);

        return commandeFournisseurMapper.toDto(commande);
    }

    @Override
    @Transactional
    public CommandeFournisseurDTO updateCommandeProduits(Long commandeId, List<ProduitQuantiteDTO> produitsMaj) {

        // 1. Fetch existing commande
        CommandeFournisseur commande = commandeFournisseurRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (!"EN_ATTENTE".equals(commande.getStatut())) {
            throw new RuntimeException("Impossible de modifier une commande déjà livrée ou annulée");
        }

        // 2. Get current produits
        List<Produit> produitsExistants = commande.getProduits();

        // 3. Convert to map for quick access
        Map<Long, Produit> mapProduitsExistants = produitsExistants.stream()
                .collect(Collectors.toMap(Produit::getId, p -> p));

        // 4. Process incoming list
        for (ProduitQuantiteDTO pq : produitsMaj) {

            Produit produit = produitRepository.findById(pq.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit avec ID " + pq.getProduitId() + " introuvable"));

            // 5. If product already exists in commande → update mouvement quantity
            Optional<MouvementStock> existingMouvement = mouvementStockRepository
                    .findByCommandeFournisseurIdAndProduitId(commandeId, produit.getId());

            if (existingMouvement.isPresent()) {
                MouvementStock mouvement = existingMouvement.get();
                mouvement.setQuantity(pq.getQuantite());
                mouvementStockRepository.save(mouvement);
            } else {
                // 6. Otherwise → add new mouvement and link produit
                MouvementStock newMvt = new MouvementStock();
                newMvt.setProduit(produit);
                newMvt.setCommandeFournisseur(commande);
                newMvt.setType(TypeMouvement.SORTIE);
                newMvt.setQuantity(pq.getQuantite());
                newMvt.setDatemovements(new Date(System.currentTimeMillis()));
                mouvementStockRepository.save(newMvt);

                if (!mapProduitsExistants.containsKey(produit.getId())) {
                    produitsExistants.add(produit);
                }
            }
        }

        // 7. Update commande’s product list
        commande.setProduits(produitsExistants);
        commande = commandeFournisseurRepository.save(commande);

        return commandeFournisseurMapper.toDto(commande);
    }



}
