package com.tricol.tricol.unit;

import com.tricol.tricol.dto.ProductCostDTO;
import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.dto.ProduitStockDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.ProductCost;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.entity.TypeMouvement;
import com.tricol.tricol.mapper.ProductCostMapper;
import com.tricol.tricol.mapper.ProduitMapper;
import com.tricol.tricol.repository.MouvementStockRepository;
import com.tricol.tricol.repository.ProductCostRepository;
import com.tricol.tricol.repository.ProduitRepository;
import com.tricol.tricol.service.impl.ProduitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProduitServiceImplTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProductCostRepository productCostRepository;

    @Mock
    private ProduitMapper produitMapper;

    @Mock
    private ProductCostMapper productCostMapper;

    @Mock
    private MouvementStockRepository mouvementStockRepository;

    @InjectMocks
    private ProduitServiceImpl produitService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testSave_createsNewProduitAndCosts() {
//
//        ProduitStockDTO dto = new ProduitStockDTO();
//        dto.setNom("PC Gamer");
//        dto.setDescription("High-end PC");
//        dto.setCategorie("Tech");
//        dto.setTotalUnits(10);
//        dto.setPrixUnitaire(2000.0);
//
//
//        when(produitRepository.findByNom("PC Gamer")).thenReturn(null);
//
//        Produit savedProduit = new Produit();
//        savedProduit.setId(1L);
//        savedProduit.setNom("PC Gamer");
//        when(produitRepository.save(any())).thenReturn(savedProduit);
//
//
//        when(productCostRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//
//        when(mouvementStockRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        ProduitStockDTO returned = new ProduitStockDTO();
//        returned.setId(1L);
//        returned.setNom("PC Gamer");
//        when(produitRepository.findByNom("PC Gamer")).thenReturn(savedProduit);
//
//        ProduitStockDTO result = produitService.save(dto);
//
//        assertNotNull(result);
//        assertEquals("PC Gamer", result.getNom());
//
//
//        verify(produitRepository, times(1)).save(any());
//        verify(productCostRepository, times(1)).save(any());
//        verify(mouvementStockRepository, times(1)).save(any());
//    }
//


    @Test
    void testSave_createsNewProduitAndCosts() {
        ProduitStockDTO dto = new ProduitStockDTO();
        dto.setNom("PC Gamer");
        dto.setDescription("High-end PC");
        dto.setCategorie("Tech");
        dto.setTotalUnits(10);
        dto.setPrixUnitaire(2000.0);

        when(produitRepository.findByNom("PC Gamer")).thenReturn(null);

        Produit savedProduit = new Produit();
        savedProduit.setId(1L);
        savedProduit.setNom("PC Gamer");
        when(produitRepository.save(any())).thenReturn(savedProduit);

        when(productCostRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mouvementStockRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProduitStockDTO result = produitService.save(dto);

        assertNotNull(result);
        assertEquals("PC Gamer", result.getNom());

        verify(produitRepository, times(1)).save(any());
        verify(productCostRepository, times(1)).save(any());
        verify(mouvementStockRepository, times(1)).save(any());
    }


    @Test
    void testSave_existingProduit_createsCostsAndMouvement() {
        ProduitStockDTO dto = new ProduitStockDTO();
        dto.setNom("PC Gamer");
        dto.setTotalUnits(5);
        dto.setPrixUnitaire(1500.0);

        Produit existingProduit = new Produit();
        existingProduit.setId(1L);
        existingProduit.setNom("PC Gamer");

        when(produitRepository.findByNom("PC Gamer")).thenReturn(existingProduit);
        when(productCostRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mouvementStockRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProduitStockDTO result = produitService.save(dto);

        assertNotNull(result);
        assertEquals("PC Gamer", result.getNom());


        verify(produitRepository, never()).save(any());


        verify(productCostRepository, times(1)).save(any());
        verify(mouvementStockRepository, times(1)).save(any());
    }




    @Test
    void testFindById_success() {

        Produit produit = new Produit();
        produit.setId(1L);

        ProductCost cost = new ProductCost();
        cost.setRemainingUnits(5);
        produit.setCosts(List.of(cost));

        ProduitDTO dto = new ProduitDTO();
        dto.setId(1L);

        ProductCostDTO costDto = new ProductCostDTO();
        costDto.setRemainingUnits(5);

        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(produitMapper.toDto(produit)).thenReturn(dto);
        when(productCostMapper.toDto(cost)).thenReturn(costDto);

        ProduitDTO result = produitService.findById(1L);

        assertNotNull(result);
        assertEquals(5, result.getStockTotal());
        verify(produitRepository).findById(1L);
    }


    @Test
    void testFindAll_pageable() {

        Produit produit = new Produit();
        produit.setId(1L);

        ProductCost cost = new ProductCost();
        cost.setRemainingUnits(3);
        produit.setCosts(List.of(cost));

        Page<Produit> produits = new PageImpl<>(List.of(produit));
        Pageable pageable = PageRequest.of(0, 10);

        ProduitDTO dto = new ProduitDTO();
        dto.setId(1L);

        ProductCostDTO costDto = new ProductCostDTO();
        costDto.setRemainingUnits(3);

        when(produitRepository.findAll(pageable)).thenReturn(produits);
        when(produitMapper.toDto(produit)).thenReturn(dto);
        when(productCostMapper.toDto(cost)).thenReturn(costDto);

        Page<ProduitDTO> result = produitService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(3, result.getContent().get(0).getStockTotal());
    }


    @Test
    void testDelete() {

        ProductCost cost = new ProductCost();
        cost.setId(10L);

        when(productCostRepository.findByProduitId(1L)).thenReturn(List.of(cost));

        produitService.delete(1L);

        verify(productCostRepository).delete(cost);
        verify(produitRepository).deleteById(1L);
    }


    @Test
    void testFilter_noCriteria_returnsAll() {
        Produit p1 = new Produit();
        when(produitRepository.findAll()).thenReturn(List.of(p1));

        List<Produit> result = produitService.filter(null, null);

        assertEquals(1, result.size());
    }

    @Test
    void testFindById_notFound_throwsException() {
        Long produitId = 1L;


        when(produitRepository.findById(produitId)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produitService.findById(produitId);
        });

        assertEquals("Produit not found", exception.getMessage());


        verify(produitRepository, times(1)).findById(produitId);
    }


    @Test
    void testFilter_withNom_andCategorie() {
        Produit p = new Produit();
        when(produitRepository.findAll(ArgumentMatchers.<Specification<Produit>>any())).thenReturn(List.of(p));

        List<Produit> result = produitService.filter("PC", "Tech");

        assertEquals(1, result.size());
        assertSame(p, result.get(0));
        verify(produitRepository, times(1)).findAll(ArgumentMatchers.<Specification<Produit>>any());
    }

}
