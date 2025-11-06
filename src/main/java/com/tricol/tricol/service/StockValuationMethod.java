package com.tricol.tricol.service;

import com.tricol.tricol.dto.ProductStockReportDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.ProductCost;

import java.util.List;

@FunctionalInterface
public interface StockValuationMethod {

     ProductStockReportDTO calculateTotal(List<ProductCost> productCosts, List<MouvementStock> mouvements);
}
