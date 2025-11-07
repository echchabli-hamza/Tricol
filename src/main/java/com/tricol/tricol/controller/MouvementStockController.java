package com.tricol.tricol.controller;

import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.dto.ProductStockReportDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.ProductCost;
import com.tricol.tricol.entity.TypeMouvement;
import com.tricol.tricol.repository.MouvementStockRepository;
import com.tricol.tricol.repository.ProductCostRepository;
import com.tricol.tricol.service.MouvementStockService;
import com.tricol.tricol.service.impl.CalculService;
import com.tricol.tricol.service.impl.MouvementStockServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class MouvementStockController {

    private final CalculService calculService;
    private final MouvementStockServiceImpl mouvementStockServiceImpl;

    public MouvementStockController(CalculService calculService, MouvementStockServiceImpl mouvementStockServiceImpl) {
        this.calculService = calculService;
        this.mouvementStockServiceImpl = mouvementStockServiceImpl;
    }

    @GetMapping("/report/{produitId}")
    public ProductStockReportDTO getProductStockReport(@PathVariable("produitId") Long produitId) {

        return calculService.calculateForProduct(produitId);
    }

    @GetMapping("/one/{produitId}")
    public List<MouvementStockDTO> getProductMovement(@PathVariable("produitId") Long produitId) {

        return  mouvementStockServiceImpl.findByProduitId(produitId);
    }
//    @GetMapping("/all")
//    public List<MouvementStockDTO> getAllMovement() {
//
//        return  mouvementStockServiceImpl.findAll();
//    }

    @GetMapping("/all")
    public Page<MouvementStockDTO> getAllMovement(Pageable pageable) {
        return mouvementStockServiceImpl.findAllM(pageable);
    }



    @GetMapping("/mvm/filter")
    public  List<MouvementStockDTO> filterMVM(@RequestParam("type") String s){

        return mouvementStockServiceImpl.filter(TypeMouvement.valueOf(s));
    }





}



