package com.tricol.tricol.entity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    private Integer quantity;


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
}
