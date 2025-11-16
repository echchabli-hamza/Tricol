package com.tricol.tricol.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.dto.ProductStockReportDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.entity.TypeMouvement;
import com.tricol.tricol.repository.MouvementStockRepository;
import com.tricol.tricol.repository.ProduitRepository;
import com.tricol.tricol.service.impl.CalculService;
import com.tricol.tricol.service.impl.MouvementStockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MouvementStockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @MockitoBean
    private CalculService calculService;

    @Autowired
    private MouvementStockServiceImpl mouvementStockServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private Produit produit1;
    private Produit produit2;

    @BeforeEach
    void setup() {
        mouvementStockRepository.deleteAll();
//        produitRepository.deleteAll();

        produit1 = new Produit();
        produit1.setNom("Produit A");
        produit1 = produitRepository.save(produit1);

        produit2 = new Produit();
        produit2.setNom("Produit B");
        produit2 = produitRepository.save(produit2);

        MouvementStock m1 = new MouvementStock();
        m1.setProduit(produit1);
        m1.setType(TypeMouvement.ENTREE);
        m1.setQuantity(10);
        m1.setDatemovements(new Date(System.currentTimeMillis()));

        MouvementStock m2 = new MouvementStock();
        m2.setProduit(produit2);
        m2.setType(TypeMouvement.SORTIE);
        m2.setQuantity(5);
        m2.setDatemovements(new Date(System.currentTimeMillis()));

        mouvementStockRepository.saveAll(List.of(m1, m2));
    }

    @Test
    void testGetProductMovement() throws Exception {
        mockMvc.perform(get("/api/stock/one/" + produit1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].produitId").value(produit1.getId()))
                .andExpect(jsonPath("$[0].type").value("ENTREE"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    @Test
    void testGetAllMovement() throws Exception {
        mockMvc.perform(get("/api/stock/all")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void testFilterMVM() throws Exception {
        mockMvc.perform(get("/api/stock/mvm/filter")
                        .param("type", "ENTREE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("ENTREE"));
    }


    @Test
    void testGetProductStockReport() throws Exception {
        ProductStockReportDTO report = new ProductStockReportDTO();
        report.setProduitId(produit1.getId());
        report.setTotalPurchasedUnits(10);
        report.setTotalSoldUnits(5);
        report.setTotalRevenue(100.0);

        when(calculService.calculateForProduct(produit1.getId())).thenReturn(report);

        mockMvc.perform(get("/api/stock/report/" + produit1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produitId").value(produit1.getId()));
    }
}
