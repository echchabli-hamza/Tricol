package com.tricol.tricol.dto;

import java.time.LocalDate;

public class MouvementStockDTO {
    private Long id;
    private Long produitId;
    private String type; // ENTREE / SORTIE / AJUSTEMENT
    private Integer quantite;
    private LocalDate dateMouvement;
    private Long commandeFournisseurId;

    public Long getCommandeFournisseurId() {
        return commandeFournisseurId;
    }

    public void setCommandeFournisseurId(Long commandeFournisseurId) {
        this.commandeFournisseurId = commandeFournisseurId;
    }

    public LocalDate getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDate dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
