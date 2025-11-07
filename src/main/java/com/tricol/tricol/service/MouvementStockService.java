package com.tricol.tricol.service;

import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.entity.TypeMouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MouvementStockService {
    MouvementStockDTO save(MouvementStockDTO dto);
    MouvementStockDTO findById(Long id);
    List<MouvementStockDTO> findAll();
    void delete(Long id);

    Page<MouvementStockDTO> findAllM(Pageable pageable);

    List<MouvementStockDTO> findByProduitId(Long produitId);
    List<MouvementStockDTO> filter(TypeMouvement state);
}
