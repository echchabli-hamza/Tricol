package com.tricol.tricol.service;

import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.dto.ProduitStockDTO;
import com.tricol.tricol.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProduitService {
    ProduitStockDTO save(ProduitStockDTO dto);

    ProduitDTO findById(Long id);

    Page<ProduitDTO> findAll(Pageable pageable);

    void delete(Long id);

    List<Produit> filter(String nom, String categorie);

}
