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

        // 1. Extract all requested product IDs
        List<Long> requestedIds = inputDTO.getProduits()
                .stream()
                .map(ProduitQuantiteDTO::getProduitId)
                .toList();

        // 2. Fetch existing products from DB
        List<Produit> produits = produitRepository.findAllById(requestedIds);

        // 3. Detect missing IDs
        Set<Long> foundIds = produits.stream()
                .map(Produit::getId)
                .collect(Collectors.toSet());

        List<Long> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new RuntimeException("Produits " + missingIds + " n'existent pas");
        }

        // 4. Fetch fournisseur
        Fournisseur fournisseur = fournisseurRepository.findById(inputDTO.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur n'existe pas"));

        // 5. Create and save the Commande
        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setDateCommande(inputDTO.getDateCommande());
        commande.setFournisseur(fournisseur);
        commande.setProduits(produits);
        commande = commandeFournisseurRepository.save(commande);
        double total = 0.0;
        // 6. For each product, record movement (ENTRÉE)
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

                    total += used*batch.getPrixUnitaire();

                    batch.setRemainingUnits(available - used);
                    productCostRepository.save(batch);

                    restant -= used;
                }



                if (restant > 0) {
                    throw new RuntimeException("Stock insuffisant pour le produit " + produit.getNom());
                }

                MouvementStock mouvement = new MouvementStock();
                mouvement.setProduit(produit);
                mouvement.setType(TypeMouvement.SORTIE);
                mouvement.setQuantity(pq.getQuantite());
                mouvementStockRepository.save(mouvement);
            }

        }
        commande.setStatut("EN_ATTENTE");
        commande.setMontantTotal(total);
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


}
