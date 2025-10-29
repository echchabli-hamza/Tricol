package com.tricol.tricol.entities;

import com.tricol.tricol.entity.CommandeFournisseur;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String societe;
    private String adresse;
    private String contact;
    private String email;
    private String telephone;
    private String ville;
    private String ice;

    @OneToMany(mappedBy = "fournisseur")
    private List<CommandeFournisseur> commandes;

    // Getters and setters
}
