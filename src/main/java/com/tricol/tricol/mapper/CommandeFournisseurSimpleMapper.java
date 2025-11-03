package com.tricol.tricol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.tricol.tricol.dto.CommandeFournisseurSimpleDTO;
import com.tricol.tricol.entity.CommandeFournisseur;

@Mapper(componentModel = "spring")
public interface CommandeFournisseurSimpleMapper {

    CommandeFournisseurSimpleMapper INSTANCE = Mappers.getMapper(CommandeFournisseurSimpleMapper.class);

    CommandeFournisseurSimpleDTO toDto(CommandeFournisseur entity);

    CommandeFournisseur toEntity(CommandeFournisseurSimpleDTO dto);
}
