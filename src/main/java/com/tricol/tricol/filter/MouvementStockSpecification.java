package com.tricol.tricol.filter;
import com.tricol.tricol.entity.MouvementStock;


import com.tricol.tricol.entity.TypeMouvement;
import org.springframework.data.jpa.domain.Specification;

public class MouvementStockSpecification {

    public static Specification<MouvementStock> hasType(TypeMouvement type) {
        return (root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.get("type"), type);
    }
}
