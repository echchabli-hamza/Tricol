package com.tricol.tricol.service.BusninessLogic;

import com.tricol.tricol.dto.ProductStockReportDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.ProductCost;
import com.tricol.tricol.entity.TypeMouvement;
import com.tricol.tricol.service.StockValuationMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CumpMethod implements StockValuationMethod {

    @Override
    public ProductStockReportDTO calculateTotal(List<ProductCost> productCosts, List<MouvementStock> mouvements) {

        ProductStockReportDTO dto = new ProductStockReportDTO();

        if (productCosts == null || productCosts.isEmpty()) return dto;

        dto.setProduitId(productCosts.get(0).getProduit().getId());


        int totalPurchasedUnits = productCosts.stream()
                .mapToInt(ProductCost::getTotalUnits)
                .sum();

        double totalPurchasedCost = productCosts.stream()
                .mapToDouble(pc -> pc.getTotalUnits() * pc.getPrixUnitaire())
                .sum();

        dto.setTotalPurchasedUnits(totalPurchasedUnits);
        dto.setTotalPurchasedCost(totalPurchasedCost);

        double averagePrice = totalPurchasedUnits > 0 ? totalPurchasedCost / totalPurchasedUnits : 0.0;
        dto.setAveragePrice(averagePrice);


        int totalSoldUnits = mouvements.stream()
                .filter(m -> m.getType() == TypeMouvement.SORTIE)
                .mapToInt(MouvementStock::getQuantity)
                .sum();
        dto.setTotalSoldUnits(totalSoldUnits);


        double totalRevenue = totalSoldUnits * averagePrice;
        dto.setTotalRevenue(totalRevenue);

        return dto;
    }
}
