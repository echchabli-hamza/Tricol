package com.tricol.tricol.filter;

import com.tricol.tricol.entity.Produit;
import org.springframework.data.jpa.domain.Specification;

public class ProduitSpecification {

    public static Specification<Produit> hasNom(String nom) {
        return (root, query, criteriaBuilder) ->
                nom == null ? null : criteriaBuilder.equal(root.get("nom"), nom);
    }

    public static Specification<Produit> hasCategorie(String categorie) {
        return (root, query, criteriaBuilder) ->
                categorie == null ? null : criteriaBuilder.equal(root.get("categorie"), categorie);
    }
}
