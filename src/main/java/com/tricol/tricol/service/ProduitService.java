package com.tricol.tricol.service;

import com.tricol.tricol.dto.ProduitDTO;
import java.util.List;

public interface ProduitService {
    ProduitDTO save(ProduitDTO dto);
    ProduitDTO findById(Long id);
    List<ProduitDTO> findAll();
    void delete(Long id);
}
