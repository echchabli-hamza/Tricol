package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.ProduitCommandeDTO;
import com.tricol.tricol.entity.MouvementStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProduitCommandeMapper {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "quantity", target = "quantite")
    ProduitCommandeDTO toDto(MouvementStock mouvement);

}
