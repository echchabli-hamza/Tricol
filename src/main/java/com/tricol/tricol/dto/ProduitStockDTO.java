package com.tricol.tricol.dto;

import lombok.Data;

@Data
public class ProduitStockDTO {

    private Long id;            // Produit ID
    private String nom;
    private String description;
    private String categorie;

    // Stock info from ProductCost
    private Double prixUnitaire;  // Latest price or average price
    private Integer totalUnits;   // Total units added
    private Integer remainingUnits; // Current remaining units

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getRemainingUnits() {
        return remainingUnits;
    }

    public void setRemainingUnits(Integer remainingUnits) {
        this.remainingUnits = remainingUnits;
    }

    public Integer getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(Integer totalUnits) {
        this.totalUnits = totalUnits;
    }
}
