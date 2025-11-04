package com.tricol.tricol.service;

import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.dto.ProduitStockDTO;

import java.util.List;

public interface ProduitService {
    ProduitStockDTO save(ProduitStockDTO dto);

    ProduitDTO findById(Long id);

    List<ProduitDTO> findAll();

    void delete(Long id);

}
