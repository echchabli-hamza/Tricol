package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.FournisseurDTO;
import com.tricol.tricol.entity.Fournisseur;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {CommandeFournisseurSimpleMapper.class})
public interface FournisseurMapper {
    FournisseurDTO toDto(Fournisseur fournisseur);
    Fournisseur toEntity(FournisseurDTO dto);
}
