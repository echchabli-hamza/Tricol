package com.tricol.tricol.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
public class ProductCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prixUnitaire;


    private Integer totalUnits;


    private Integer remainingUnits;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

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

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
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
