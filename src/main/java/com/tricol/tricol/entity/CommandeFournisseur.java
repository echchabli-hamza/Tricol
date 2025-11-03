package com.tricol.tricol.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data

@Entity
public class CommandeFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateCommande;
    private String statut;
    private Double montantTotal;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToMany
    @JoinTable(
            name = "commande_produit",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private List<Produit> produits;

    // Getters and Setters
}
