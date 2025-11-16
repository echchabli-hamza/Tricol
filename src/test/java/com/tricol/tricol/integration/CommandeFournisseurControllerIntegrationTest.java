package com.tricol.tricol.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricol.controller.CommandeFournisseurController;
import com.tricol.tricol.dto.CommandeFournisseurInputDTO;
import com.tricol.tricol.dto.CommandeFournisseurDTO;
import com.tricol.tricol.dto.ProduitQuantiteDTO;
import com.tricol.tricol.entity.StatutCommande;
import com.tricol.tricol.service.CommandeFournisseurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommandeFournisseurControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommandeFournisseurService commandeFournisseurService;

    private CommandeFournisseurDTO sampleDTO;

    @BeforeEach
    void setup() {
        sampleDTO = new CommandeFournisseurDTO();
        sampleDTO.setId(1L);
        sampleDTO.setStatut(StatutCommande.EN_ATTENTE.name());
    }

    @Test
    void testCreateCommande() throws Exception {
        // Préparer le DTO d'entrée avec les champs obligatoires
        CommandeFournisseurInputDTO inputDTO = new CommandeFournisseurInputDTO();
        inputDTO.setDateCommande(LocalDate.now());
        inputDTO.setFournisseurId(1L);
        inputDTO.setProduits(List.of(new ProduitQuantiteDTO(1L, 5)));

        // Mock du service
        Mockito.when(commandeFournisseurService.save(any(CommandeFournisseurInputDTO.class)))
                .thenReturn(sampleDTO);

        mockMvc.perform(post("/api/commandes-fournisseur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"));
    }

    @Test
    void testGetAllCommandes() throws Exception {
        Page<CommandeFournisseurDTO> page = new PageImpl<>(List.of(sampleDTO));
        Mockito.when(commandeFournisseurService.findAll(any(PageRequest.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/commandes-fournisseur")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void testGetCommandeById() throws Exception {
        Mockito.when(commandeFournisseurService.findById(1L))
                .thenReturn(sampleDTO);

        mockMvc.perform(get("/api/commandes-fournisseur/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testLivrerCommande() throws Exception {
        sampleDTO.setStatut(StatutCommande.LIVREE.name());
        Mockito.when(commandeFournisseurService.deliverCommande(1L))
                .thenReturn(sampleDTO);

        mockMvc.perform(put("/api/commandes-fournisseur/1/livrer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut").value("LIVREE"));
    }

    @Test
    void testUpdateProduits() throws Exception {
        List<ProduitQuantiteDTO> produitsMaj = List.of(new ProduitQuantiteDTO(1L, 5));
        Mockito.when(commandeFournisseurService.updateCommandeProduits(eq(1L), any()))
                .thenReturn(sampleDTO);

        mockMvc.perform(put("/api/commandes-fournisseur/1/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produitsMaj)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testFilterCommandes() throws Exception {
        Mockito.when(commandeFournisseurService.filter(StatutCommande.EN_ATTENTE))
                .thenReturn(List.of(sampleDTO));

        mockMvc.perform(get("/api/commandes-fournisseur/filter")
                        .param("state", "EN_ATTENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].statut").value("EN_ATTENTE"));
    }
}
