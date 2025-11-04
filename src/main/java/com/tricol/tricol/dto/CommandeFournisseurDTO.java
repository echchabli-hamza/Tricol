package com.tricol.tricol.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CommandeFournisseurDTO {
    private Long id;
    private LocalDate dateCommande;
    private String statut;
    private Double montantTotal;
    private Long fournisseurId;


    private List<ProduitCommandeDTO> produits;

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public List<ProduitCommandeDTO> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitCommandeDTO> produits) {
        this.produits = produits;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
