package com.tricol.tricol.dto;


import java.util.List;

public class ProduitDTO {
    private Long id;
    private String nom;
    private String description;
    private String categorie;

    // Optional derived data
    private Double prixMoyen; // average cost or price if you want to show it
    private Integer stockTotal; // total from remainingUnits or mouvements

    // Relationships
    private List<ProductCostDTO> costs; // all cost batches
    private List<MouvementStockDTO> mouvements; // all stock movements

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Double getPrixMoyen() {
        return prixMoyen;
    }
    public void setPrixMoyen(Double prixMoyen) {
        this.prixMoyen = prixMoyen;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }
    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public List<ProductCostDTO> getCosts() {
        return costs;
    }
    public void setCosts(List<ProductCostDTO> costs) {
        this.costs = costs;
    }

    public List<MouvementStockDTO> getMouvements() {
        return mouvements;
    }
    public void setMouvements(List<MouvementStockDTO> mouvements) {
        this.mouvements = mouvements;
    }
}
