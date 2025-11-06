//package com.tricol.tricol.config;
//
//import com.tricol.tricol.service.BusninessLogic.CumpMethod;
//import com.tricol.tricol.service.BusninessLogic.FifoMethod;
//import com.tricol.tricol.service.StockValuationMethod;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class StockConfig {
//
//    @Value("${stock.valuation.method:CUMP}")
//    private String method;
//
//    @Bean
//    public StockValuationMethod stockValuationMethod(CumpMethod cump, FifoMethod fifo) {
//        if ("FIFO".equalsIgnoreCase(method)) {
//            return fifo;
//        } else {
//            return cump;
//        }
//    }
//}
