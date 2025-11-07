package com.tricol.tricol.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class ProduitDTO {
    private Long id;
    @NotBlank(message = "nom is required")
    private String nom;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "description is required")
    private String categorie;

    @NotBlank(message = "description is required")
    private Double prixMoyen;
    @NotBlank(message = "description is required")
    private Integer stockTotal;


    private List<ProductCostDTO> costs;
    private List<MouvementStockDTO> mouvements;

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
