package com.tricol.tricol.service;

import com.tricol.tricol.dto.FournisseurDTO;
import java.util.List;

public interface FournisseurService {
    FournisseurDTO save(FournisseurDTO dto);
    FournisseurDTO findById(Long id);
    List<FournisseurDTO> findAll();
    void delete(Long id);
}
