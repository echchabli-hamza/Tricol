package com.tricol.tricol.dto;

import java.time.LocalDate;
import java.util.List;

public class CommandeFournisseurDTO {
    private Long id;
    private LocalDate dateCommande;
    private String statut;
    private Long fournisseurId;

    private List<ProduitCommandeDTO> produits; // embedded product list

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<ProduitCommandeDTO> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitCommandeDTO> produits) {
        this.produits = produits;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }
}
