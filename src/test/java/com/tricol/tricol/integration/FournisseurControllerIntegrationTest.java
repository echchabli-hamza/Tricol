package com.tricol.tricol.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricol.dto.FournisseurDTO;
import com.tricol.tricol.entity.Fournisseur;
import com.tricol.tricol.repository.FournisseurRepository;
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
class FournisseurControllerIntegrationTest {

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        fournisseurRepository.deleteAll();
    }

    @Test
    void testCreateFournisseur() throws Exception {
        FournisseurDTO dto = new FournisseurDTO();
        dto.setContact("Fournisseur A");
        dto.setEmail("fournisseur@example.com"); // obligatoire
        dto.setSociete("Societe A");             // si DTO a @NotBlank
        dto.setAdresse("123 rue Exemple");       // si obligatoire
        dto.setTelephone("0600000000");          // si obligatoire
        dto.setVille("Casablanca");              // si obligatoire
        dto.setIce("ICE123456");                 // si obligatoire

        mockMvc.perform(post("/api/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contact").value("Fournisseur A"))
                .andExpect(jsonPath("$.email").value("fournisseur@example.com"));

        assertThat(fournisseurRepository.count()).isEqualTo(1);
    }


    @Test
    void testGetFournisseurById() throws Exception {
        Fournisseur f = new Fournisseur();
        f.setContact("Fournisseur B");
        f = fournisseurRepository.save(f);

        mockMvc.perform(get("/api/fournisseurs/" + f.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contact").value("Fournisseur B"));
    }

    @Test
    void testGetAllFournisseurs() throws Exception {
        Fournisseur f1 = new Fournisseur();
        f1.setContact("A");
        fournisseurRepository.save(f1);

        Fournisseur f2 = new Fournisseur();
        f2.setContact("B");
        fournisseurRepository.save(f2);

        mockMvc.perform(get("/api/fournisseurs")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void testDeleteFournisseur() throws Exception {
        Fournisseur f = new Fournisseur();
        f.setContact("C");
        f = fournisseurRepository.save(f);

        mockMvc.perform(delete("/api/fournisseurs/" + f.getId()))
                .andExpect(status().isNoContent());

        assertThat(fournisseurRepository.count()).isEqualTo(0);
    }
}
