package com.tricol.tricol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class CommandeFournisseurInputDTO {
    private Long id;
    @NotNull(message = "dateCommande is required")
    private LocalDate dateCommande;
    @NotEmpty(message = "produits is required")
    private List<ProduitQuantiteDTO> produits;
    @NotNull(message = "fournisseurId is required")
    private Long fournisseurId;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }
    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public List<ProduitQuantiteDTO> getProduits() {
        return produits;
    }
    public void setProduits(List<ProduitQuantiteDTO> produits) {
        this.produits = produits;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }
}
