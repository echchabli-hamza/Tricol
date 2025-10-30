package com.tricol.tricol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.tricol.tricol.dto.FournisseurDTO;
import com.tricol.tricol.entity.Fournisseur;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {

    FournisseurMapper INSTANCE = Mappers.getMapper(FournisseurMapper.class);

    FournisseurDTO toDto(Fournisseur fournisseur);

    Fournisseur toEntity(FournisseurDTO dto);
}
