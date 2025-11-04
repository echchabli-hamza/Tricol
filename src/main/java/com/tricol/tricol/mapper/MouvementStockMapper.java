package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.entity.MouvementStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "commandeFournisseur.id", target = "commandeFournisseurId")
    MouvementStockDTO toDto(MouvementStock entity);

    @Mapping(source = "produitId", target = "produit.id")
    @Mapping(source = "commandeFournisseurId", target = "commandeFournisseur.id")
    MouvementStock toEntity(MouvementStockDTO dto);
}

