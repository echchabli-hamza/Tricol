package com.tricol.tricol.filter;

import com.tricol.tricol.entity.CommandeFournisseur;
import com.tricol.tricol.entity.StatutCommande;

import org.springframework.data.jpa.domain.Specification;


public class CommandSpecification {

    public static Specification<CommandeFournisseur> hasState(StatutCommande s){

        return (root, query, criteriaBuilder) ->
                s == null ? null : criteriaBuilder.equal(root.get("state"), s);

    }


}
