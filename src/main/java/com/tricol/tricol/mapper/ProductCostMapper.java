package com.tricol.tricol.mapper;

import com.tricol.tricol.dto.ProductCostDTO;
import com.tricol.tricol.entity.ProductCost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface ProductCostMapper {
    ProductCostDTO toDto(ProductCost entity);
    ProductCost toEntity(ProductCostDTO dto);
}
