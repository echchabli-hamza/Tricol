package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.entity.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" ,uses = {MouvementStockMapper.class})
public interface ProduitMapper {
     ProduitDTO toDto(Produit produit);
    Produit toEntity(ProduitDTO dto);
}
