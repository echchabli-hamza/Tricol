package com.tricol.tricol.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private String categorie;

    @ManyToMany(mappedBy = "produits")
    private List<CommandeFournisseur> commandes;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<ProductCost> costs;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<MouvementStock> mouvements;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public List<CommandeFournisseur> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<CommandeFournisseur> commandes) {
        this.commandes = commandes;
    }

    public List<ProductCost> getCosts() {
        return costs;
    }

    public void setCosts(List<ProductCost> costs) {
        this.costs = costs;
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

    public List<MouvementStock> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<MouvementStock> mouvements) {
        this.mouvements = mouvements;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
