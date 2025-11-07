package com.tricol.tricol.controller;

import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.dto.ProduitStockDTO;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.mapper.ProduitMapper;
import com.tricol.tricol.service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;
     private final ProduitMapper produitMapper ;
    public ProduitController(ProduitService produitService, ProduitMapper produitMapper) {
        this.produitService = produitService;
        this.produitMapper = produitMapper;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProduitStockDTO> create(@Valid  @RequestBody ProduitStockDTO dto) {
        ProduitStockDTO saved = produitService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getById(@PathVariable Long id) {
        ProduitDTO dto = produitService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // Get all products
    @GetMapping
    public Page<ProduitDTO> getAll(Pageable pageable) {
       return produitService.findAll(pageable);
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filter")
    public ResponseEntity<List<ProduitDTO>> filter(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String categorie) {

        List<Produit> produits = produitService.filter(nom, categorie);


        List<ProduitDTO> dtos = produits.stream()
                .map(produitMapper::toDto)
                .peek(e-> {e.setCosts(null)  ; e.setMouvements(null) ;   })
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
