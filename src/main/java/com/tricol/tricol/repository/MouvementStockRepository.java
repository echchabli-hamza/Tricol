package com.tricol.tricol.repository;

import com.tricol.tricol.entity.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    List<MouvementStock> findByProduitId(Long produitId);
}
