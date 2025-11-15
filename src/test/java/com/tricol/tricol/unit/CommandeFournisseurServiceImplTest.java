package com.tricol.tricol.unit;



import com.tricol.tricol.dto.*;
import com.tricol.tricol.entity.*;
import com.tricol.tricol.mapper.CommandeFournisseurMapper;
import com.tricol.tricol.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.tricol.tricol.service.impl.CommandeFournisseurServiceImpl;

import java.sql.Date;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandeFournisseurServiceImplTest {

    private CommandeFournisseurRepository commandeRepo;
    private ProduitRepository produitRepo;
    private CommandeFournisseurMapper mapper;
    private FournisseurRepository fournisseurRepo;
    private MouvementStockRepository mouvementRepo;
    private ProductCostRepository productCostRepo;

    private CommandeFournisseurServiceImpl service;

    @BeforeEach
    void setUp() {
        commandeRepo = mock(CommandeFournisseurRepository.class);
        produitRepo = mock(ProduitRepository.class);
        mapper = mock(CommandeFournisseurMapper.class);
        fournisseurRepo = mock(FournisseurRepository.class);
        mouvementRepo = mock(MouvementStockRepository.class);
        productCostRepo = mock(ProductCostRepository.class);

        service = new CommandeFournisseurServiceImpl(
                commandeRepo, produitRepo, mapper, fournisseurRepo, mouvementRepo, productCostRepo
        );
    }

    @Test
    void testSave_success() {

        CommandeFournisseurInputDTO input = new CommandeFournisseurInputDTO();
        input.setFournisseurId(1L);
        input.setDateCommande(new Date(System.currentTimeMillis()).toLocalDate());

        ProduitQuantiteDTO pqDto = new ProduitQuantiteDTO();
        pqDto.setProduitId(1L);
        pqDto.setQuantite(5);

        input.setProduits(List.of(pqDto));


        Produit produit = new Produit();
        produit.setId(1L);

        when(produitRepo.findAllById(List.of(1L))).thenReturn(List.of(produit));

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);
        when(fournisseurRepo.findById(1L)).thenReturn(Optional.of(fournisseur));

        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setId(10L);
        when(commandeRepo.save(any())).thenReturn(commande);

        when(productCostRepo.findByProduitIdAndRemainingUnitsGreaterThanOrderByIdAsc(1L, 0))
                .thenReturn(List.of(new ProductCost() {{
                    setRemainingUnits(5);
                    setPrixUnitaire(100.0);
                }}));

        when(mapper.toDto(commande)).thenReturn(new CommandeFournisseurDTO());


        CommandeFournisseurDTO result = service.save(input);


        assertNotNull(result);
        verify(commandeRepo).save(any());
        verify(mouvementRepo).save(any());
    }

    @Test
    void testSave_missingProduct_throwsException() {
        CommandeFournisseurInputDTO input = new CommandeFournisseurInputDTO();
        input.setFournisseurId(1L);

        ProduitQuantiteDTO pqDto = new ProduitQuantiteDTO();
        pqDto.setProduitId(42L);
        pqDto.setQuantite(1);
        input.setProduits(List.of(pqDto));

        when(produitRepo.findAllById(List.of(42L))).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.save(input));
        assertTrue(ex.getMessage().contains("Produits [42] n'existent pas"));
    }

    @Test
    void testFindById_success() {
        CommandeFournisseur cmd = new CommandeFournisseur();
        cmd.setId(1L);

        when(commandeRepo.findById(1L)).thenReturn(Optional.of(cmd));
        when(mapper.toDto(cmd)).thenReturn(new CommandeFournisseurDTO());

        CommandeFournisseurDTO dto = service.findById(1L);
        assertNotNull(dto);
    }

    @Test
    void testFindById_notFound() {
        when(commandeRepo.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findById(1L));
        assertTrue(ex.getMessage().contains("Commande fournisseur introuvable"));
    }

    @Test
    void testFindAll() {
        CommandeFournisseur cmd = new CommandeFournisseur();
        List<CommandeFournisseur> list = List.of(cmd);
        Page<CommandeFournisseur> page = new PageImpl<>(list);

        when(commandeRepo.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(mapper.toDto(cmd)).thenReturn(new CommandeFournisseurDTO());

        Page<CommandeFournisseurDTO> result = service.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testDeliverCommande_success() {
        CommandeFournisseur cmd = new CommandeFournisseur();
        cmd.setId(1L);
        cmd.setStatut("EN_ATTENTE");

        Produit produit = new Produit();
        produit.setId(1L);

        MouvementStock mouvement = new MouvementStock();
        mouvement.setProduit(produit);
        mouvement.setQuantity(5);

        when(commandeRepo.findById(1L)).thenReturn(Optional.of(cmd));
        when(mouvementRepo.findByCommandeFournisseurId(1L)).thenReturn(List.of(mouvement));

        ProductCost batch = new ProductCost();
        batch.setRemainingUnits(5);
        batch.setPrixUnitaire(100.0);

        when(productCostRepo.findByProduitIdAndRemainingUnitsGreaterThanOrderByIdAsc(1L, 0))
                .thenReturn(List.of(batch));

        when(mapper.toDto(cmd)).thenReturn(new CommandeFournisseurDTO());

        CommandeFournisseurDTO dto = service.deliverCommande(1L);

        assertNotNull(dto);
        assertEquals("LIVRÉE", cmd.getStatut());
        verify(productCostRepo).save(batch);
        verify(commandeRepo).save(cmd);
    }

    @Test
    void testDeliverCommande_insufficientStock_throws() {
        CommandeFournisseur cmd = new CommandeFournisseur();
        cmd.setId(1L);
        cmd.setStatut("EN_ATTENTE");

        Produit produit = new Produit();
        produit.setId(1L);

        MouvementStock mouvement = new MouvementStock();
        mouvement.setProduit(produit);
        mouvement.setQuantity(10);

        when(commandeRepo.findById(1L)).thenReturn(Optional.of(cmd));
        when(mouvementRepo.findByCommandeFournisseurId(1L)).thenReturn(List.of(mouvement));

        ProductCost batch = new ProductCost();
        batch.setRemainingUnits(5);
        batch.setPrixUnitaire(100.0);

        when(productCostRepo.findByProduitIdAndRemainingUnitsGreaterThanOrderByIdAsc(1L, 0))
                .thenReturn(List.of(batch));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deliverCommande(1L));
        assertTrue(ex.getMessage().contains("Stock insuffisant"));
    }
}
