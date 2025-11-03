package com.tricol.tricol.repository;

import com.tricol.tricol.entity.ProductCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCostRepository extends JpaRepository<ProductCost, Long> {


    List<ProductCost> findByProduitId(Long produitId);


    List<ProductCost> findByProduitIdAndRemainingUnitsGreaterThan(Long produitId, Integer remainingUnits);

    List<ProductCost> findByProduitIdAndRemainingUnitsGreaterThanOrderByIdAsc(Long produitId, int i);
}
