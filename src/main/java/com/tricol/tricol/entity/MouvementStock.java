package com.tricol.tricol.entity;

import com.tricol.tricol.entity.TypeMouvement;
import com.tricol.tricol.entity.CommandeFournisseur;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateMouvement;
    private int quantite;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private com.tricol.tricol.entity.Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private CommandeFournisseur commande;

    // Getters and setters
}
