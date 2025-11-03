package com.tricol.tricol.dto;


public class ProductCostDTO {
    private Long id;
    private Double prixUnitaire;
    private Integer totalUnits;
    private Integer remainingUnits;

    private Long produitId; // reference only

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getTotalUnits() {
        return totalUnits;
    }
    public void setTotalUnits(Integer totalUnits) {
        this.totalUnits = totalUnits;
    }

    public Integer getRemainingUnits() {
        return remainingUnits;
    }
    public void setRemainingUnits(Integer remainingUnits) {
        this.remainingUnits = remainingUnits;
    }

    public Long getProduitId() {
        return produitId;
    }
    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }
}
