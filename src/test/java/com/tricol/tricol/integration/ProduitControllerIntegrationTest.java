package com.tricol.tricol.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricol.dto.ProduitStockDTO;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ProduitControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        produitRepository.deleteAll();
    }

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("pass");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @Test
    void testCreateProduit() throws Exception {

        ProduitStockDTO dto = new ProduitStockDTO();
        dto.setNom("PC Gamer");
        dto.setCategorie("Tech");
        dto.setTotalUnits(5);
        dto.setPrixUnitaire(2000.0);

        mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("PC Gamer"));


        assertThat(produitRepository.count()).isEqualTo(1);
    }


    @Test
    void testGetProduitById() throws Exception {


        Produit produit = new Produit();
        produit.setNom("Souris");
        produit.setCategorie("Tech");
        produit = produitRepository.save(produit);

        mockMvc.perform(get("/api/produits/" + produit.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Souris"));
    }

    @Test
    void testGetAllProduits() throws Exception {

        Produit p1 = new Produit();
        p1.setNom("A");
        produitRepository.save(p1);

        Produit p2 = new Produit();
        p2.setNom("B");
        produitRepository.save(p2);

        mockMvc.perform(get("/api/produits")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }


    @Test
    void testDeleteProduit() throws Exception {

        Produit p = new Produit();
        p.setNom("Test");
        p = produitRepository.save(p);

        mockMvc.perform(delete("/api/produits/" + p.getId()))
                .andExpect(status().isNoContent());

        assertThat(produitRepository.count()).isEqualTo(0);
    }



    @Test
    void testFilterProduits() throws Exception {

        Produit p1 = new Produit();
        p1.setNom("PC Gamer");
        p1.setCategorie("Tech");
        produitRepository.save(p1);

        Produit p2 = new Produit();
        p2.setNom("Chaise");
        p2.setCategorie("Maison");
        produitRepository.save(p2);

        mockMvc.perform(get("/api/produits/filter")
                        .param("nom", "PC Gamer")
                        .param("categorie", "Tech"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("PC Gamer"));
    }

}