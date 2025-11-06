package com.tricol.tricol.service;

import com.tricol.tricol.dto.FournisseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FournisseurService {
    FournisseurDTO save(FournisseurDTO dto);
    FournisseurDTO findById(Long id);
    Page<FournisseurDTO> findAll(Pageable pageable);
    void delete(Long id);
}
