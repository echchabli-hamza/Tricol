package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitMapper INSTANCE = Mappers.getMapper(ProduitMapper.class);

    // Ignore mouvements to prevent MapStruct errors
    @Mapping(target = "mouvements", ignore = true)
    ProduitDTO toDto(Produit produit);

    @Mapping(target = "mouvements", ignore = true)
    Produit toEntity(ProduitDTO dto);
}
