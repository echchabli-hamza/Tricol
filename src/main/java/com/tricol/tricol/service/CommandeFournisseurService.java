package com.tricol.tricol.service;

import com.tricol.tricol.dto.CommandeFournisseurDTO;
import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDTO save(CommandeFournisseurDTO dto);
    CommandeFournisseurDTO findById(Long id);
    List<CommandeFournisseurDTO> findAll();
    void delete(Long id);
}
