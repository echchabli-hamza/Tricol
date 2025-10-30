package com.tricol.tricol.controller;

import com.tricol.tricol.dto.FournisseurDTO;
import com.tricol.tricol.service.FournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tricol.tricol.service.MyService;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurService fournisseurService;
    private final MyService myService;

    public FournisseurController(FournisseurService fournisseurService , MyService myService) {
        this.fournisseurService = fournisseurService;
        this.myService = myService;
    }

    @PostMapping
    public ResponseEntity<FournisseurDTO> create(@RequestBody FournisseurDTO dto) {

        FournisseurDTO saved = fournisseurService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> getById(@PathVariable Long id) {
        FournisseurDTO fournisseur = fournisseurService.findById(id);
        return ResponseEntity.ok(fournisseur);
    }

    @GetMapping
    public ResponseEntity<List<FournisseurDTO>> getAll() {
        List<FournisseurDTO> fournisseurs = fournisseurService.findAll();
        return ResponseEntity.ok(fournisseurs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
