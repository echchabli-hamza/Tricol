package com.tricol.tricol.repository;

import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long>, JpaSpecificationExecutor<MouvementStock> {
    List<MouvementStock> findByProduitId(Long produitId);
    List<MouvementStock> findByCommandeFournisseurId(Long commandeId);



    Optional<MouvementStock> findByCommandeFournisseurIdAndProduitId(Long commandeId, Long id);
}
