package com.tricol.tricol.service.impl;

import com.tricol.tricol.dto.ProductStockReportDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.ProductCost;
import com.tricol.tricol.service.BusninessLogic.CumpMethod;
import com.tricol.tricol.repository.MouvementStockRepository;
import com.tricol.tricol.repository.ProductCostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculService {

    private final CumpMethod cumpMethod;
    private final ProductCostRepository productCostRepository;
    private final MouvementStockRepository mouvementStockRepository;

    public CalculService(CumpMethod cumpMethod,
                         ProductCostRepository productCostRepository,
                         MouvementStockRepository mouvementStockRepository) {
        this.cumpMethod = cumpMethod;
        this.productCostRepository = productCostRepository;
        this.mouvementStockRepository = mouvementStockRepository;
    }

    public ProductStockReportDTO calculateForProduct(Long produitId) {
        List<ProductCost> productCosts = productCostRepository.findByProduitId(produitId);
        List<MouvementStock> mouvements = mouvementStockRepository.findByProduitId(produitId);


        return cumpMethod.calculateTotal(productCosts, mouvements);
    }
}
