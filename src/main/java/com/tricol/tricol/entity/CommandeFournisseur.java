package com.tricol.tricol.entity;

import com.tricol.tricol.entities.Fournisseur;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class CommandeFournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateCommande;

    @Enumerated(EnumType.STRING)
    private StatutCommande statut;

    private BigDecimal montantTotal;

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

    // Getters and setters
}
