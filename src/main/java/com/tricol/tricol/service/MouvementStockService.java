package com.tricol.tricol.service;

import com.tricol.tricol.dto.MouvementStockDTO;
import java.util.List;

public interface MouvementStockService {
    MouvementStockDTO save(MouvementStockDTO dto);
    MouvementStockDTO findById(Long id);
    List<MouvementStockDTO> findAll();
    void delete(Long id);
}
