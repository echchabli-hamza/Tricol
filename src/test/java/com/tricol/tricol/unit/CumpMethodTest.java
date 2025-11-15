package com.tricol.tricol.unit;


import com.tricol.tricol.service.BusninessLogic.CumpMethod;

import com.tricol.tricol.dto.ProductStockReportDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.ProductCost;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.entity.TypeMouvement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CumpMethodTest {

    private CumpMethod cumpMethod;

    @BeforeEach
    void setUp() {
        cumpMethod = new CumpMethod();
    }

    @Test
    void testCalculateTotal_withEmptyProductCosts() {
        ProductStockReportDTO result = cumpMethod.calculateTotal(new ArrayList<>(), new ArrayList<>());
        assertNotNull(result);
        assertEquals(0, result.getTotalPurchasedUnits());
        assertEquals(0.0, result.getTotalPurchasedCost());
        assertEquals(0.0, result.getAveragePrice());
        assertEquals(0, result.getTotalSoldUnits());
        assertEquals(0.0, result.getTotalRevenue());
    }

    @Test
    void testCalculateTotal_onlyPurchases_noSales() {
        Produit produit = new Produit();
        produit.setId(1L);

        ProductCost pc1 = new ProductCost();
        pc1.setProduit(produit);
        pc1.setTotalUnits(10);
        pc1.setPrixUnitaire(100.0);

        ProductCost pc2 = new ProductCost();
        pc2.setProduit(produit);
        pc2.setTotalUnits(20);
        pc2.setPrixUnitaire(150.0);

        List<ProductCost> productCosts = List.of(pc1, pc2);
        List<MouvementStock> mouvements = new ArrayList<>();

        ProductStockReportDTO result = cumpMethod.calculateTotal(productCosts, mouvements);

        assertEquals(1, result.getProduitId());
        assertEquals(30, result.getTotalPurchasedUnits());
        assertEquals(10*100.0 + 20*150.0, result.getTotalPurchasedCost());
        assertEquals((10*100.0 + 20*150.0)/30, result.getAveragePrice());
        assertEquals(0, result.getTotalSoldUnits());
        assertEquals(0.0, result.getTotalRevenue());
    }

    @Test
    void testCalculateTotal_withSales() {
        Produit produit = new Produit();
        produit.setId(2L);

        ProductCost pc = new ProductCost();
        pc.setProduit(produit);
        pc.setTotalUnits(50);
        pc.setPrixUnitaire(200.0);

        MouvementStock m1 = new MouvementStock();
        m1.setType(TypeMouvement.SORTIE);
        m1.setQuantity(10);

        MouvementStock m2 = new MouvementStock();
        m2.setType(TypeMouvement.SORTIE);
        m2.setQuantity(5);

        MouvementStock m3 = new MouvementStock();
        m3.setType(TypeMouvement.ENTREE);
        m3.setQuantity(100);

        ProductStockReportDTO result = cumpMethod.calculateTotal(List.of(pc), List.of(m1, m2, m3));

        assertEquals(2, result.getProduitId());
        assertEquals(50, result.getTotalPurchasedUnits());
        assertEquals(50*200.0, result.getTotalPurchasedCost());
        assertEquals(200.0, result.getAveragePrice());
        assertEquals(15, result.getTotalSoldUnits());
        assertEquals(15*200.0, result.getTotalRevenue());
    }

    @Test
    void testCalculateTotal_noPurchases_withSales() {
        MouvementStock m = new MouvementStock();
        m.setType(TypeMouvement.SORTIE);
        m.setQuantity(10);

        ProductStockReportDTO result = cumpMethod.calculateTotal(new ArrayList<>(), List.of(m));


        assertEquals(0, result.getTotalPurchasedUnits());
        assertEquals(0.0, result.getAveragePrice());
        assertEquals(10, result.getTotalSoldUnits());
        assertEquals(0.0, result.getTotalRevenue());
    }
}
