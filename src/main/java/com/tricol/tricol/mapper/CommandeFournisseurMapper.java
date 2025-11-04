package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.CommandeFournisseurDTO;
import com.tricol.tricol.entity.CommandeFournisseur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FournisseurMapper.class, ProduitMapper.class , MouvementStockMapper.class })
public interface CommandeFournisseurMapper {
    CommandeFournisseurDTO toDto(CommandeFournisseur entity);
    CommandeFournisseur toEntity(CommandeFournisseurDTO dto);
}
