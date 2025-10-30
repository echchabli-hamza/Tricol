package com.tricol.tricol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.entity.MouvementStock;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class, CommandeFournisseurMapper.class})
public interface MouvementStockMapper {

    MouvementStockMapper INSTANCE = Mappers.getMapper(MouvementStockMapper.class);

    MouvementStockDTO toDto(MouvementStock entity);

    MouvementStock toEntity(MouvementStockDTO dto);
}
