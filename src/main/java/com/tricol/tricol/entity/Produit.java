package com.tricol.tricol.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private BigDecimal prixUnitaire;
    private String categorie;
    private int stockActuel;

    @ManyToMany(mappedBy = "produits")
    private List<CommandeFournisseur> commandes;

    @OneToMany(mappedBy = "produit")
    private List<MouvementStock> mouvements;

    // Getters and setters
}
