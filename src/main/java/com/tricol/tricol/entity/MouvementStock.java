package com.tricol.tricol.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data

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


}
