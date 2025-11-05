package com.tricol.tricol.service;

import com.tricol.tricol.dto.CommandeFournisseurDTO;
import com.tricol.tricol.dto.CommandeFournisseurInputDTO;
import com.tricol.tricol.dto.ProduitQuantiteDTO;

import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDTO save(CommandeFournisseurInputDTO dto);
    CommandeFournisseurDTO findById(Long id);
    List<CommandeFournisseurDTO> findAll();
    CommandeFournisseurDTO deliverCommande(Long commandeId);

    CommandeFournisseurDTO updateCommandeProduits(Long id, List<ProduitQuantiteDTO> produitsMaj);
}
