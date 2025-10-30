package com.tricol.tricol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.entity.Produit;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitMapper INSTANCE = Mappers.getMapper(ProduitMapper.class);

    ProduitDTO toDto(Produit produit);

    Produit toEntity(ProduitDTO dto);
}
