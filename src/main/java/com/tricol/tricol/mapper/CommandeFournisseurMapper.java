package com.tricol.tricol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.tricol.tricol.dto.CommandeFournisseurDTO;
import com.tricol.tricol.entity.CommandeFournisseur;

@Mapper(componentModel = "spring", uses = {FournisseurMapper.class, ProduitMapper.class})
public interface CommandeFournisseurMapper {

    CommandeFournisseurMapper INSTANCE = Mappers.getMapper(CommandeFournisseurMapper.class);

    CommandeFournisseurDTO toDto(CommandeFournisseur entity);

    CommandeFournisseur toEntity(CommandeFournisseurDTO dto);
}
