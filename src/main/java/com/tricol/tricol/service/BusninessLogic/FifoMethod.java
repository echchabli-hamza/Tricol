//package com.tricol.tricol.service.BusninessLogic;
//
//
//import com.tricol.tricol.dto.ProductStockReportDTO;
//import com.tricol.tricol.entity.MouvementStock;
//import com.tricol.tricol.entity.ProductCost;
//import com.tricol.tricol.service.StockValuationMethod;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public class FifoMethod implements StockValuationMethod {
//
//    @Override
//    public ProductStockReportDTO calculateTotal(List<ProductCost> productCosts, List<MouvementStock> mouvements){
//
//        int remaining = quantity;
//        double total = 0;
//
//        for (ProductCost batch : batches) {
//            if (remaining <= 0) break;
//
//            int used = Math.min(batch.getRemainingUnits(), remaining);
//            total += used * batch.getPrixUnitaire();
//            remaining -= used;
//        }
//
//        return total;
//    }
//}
