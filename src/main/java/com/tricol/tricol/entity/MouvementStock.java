package com.tricol.tricol.entity;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_fournisseur_id")
    private CommandeFournisseur commandeFournisseur;


    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    private Integer quantity;

    @Column(name = "datemovements")
    private Date datemovements;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TypeMouvement getType() {
        return type;
    }

    public void setType(TypeMouvement type) {
        this.type = type;
    }

    public CommandeFournisseur getCommandeFournisseur() {
        return commandeFournisseur;
    }

    public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseur = commandeFournisseur;
    }


    public Date getDatemovements() {
        return datemovements;
    }

    public void setDatemovements(Date  datemovements) {
        this.datemovements = datemovements;
    }
}
