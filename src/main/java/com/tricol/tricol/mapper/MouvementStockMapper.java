package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.entity.MouvementStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface MouvementStockMapper {
    MouvementStockDTO toDto(MouvementStock entity);
    MouvementStock toEntity(MouvementStockDTO dto);
}
