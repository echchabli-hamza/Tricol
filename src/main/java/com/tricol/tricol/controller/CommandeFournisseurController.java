package com.tricol.tricol.controller;

import com.tricol.tricol.dto.CommandeFournisseurDTO;
import com.tricol.tricol.dto.CommandeFournisseurInputDTO;
import com.tricol.tricol.dto.ProduitQuantiteDTO;
import com.tricol.tricol.service.CommandeFournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes-fournisseur")
public class CommandeFournisseurController {

    private final CommandeFournisseurService commandeFournisseurService;

    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CommandeFournisseurInputDTO inputDTO) {
        try {
            CommandeFournisseurDTO saved = commandeFournisseurService.save(inputDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CommandeFournisseurDTO>> getAll() {
        return ResponseEntity.ok(commandeFournisseurService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeFournisseurDTO> getById(@PathVariable Long id) {
        CommandeFournisseurDTO dto = commandeFournisseurService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{commandeId}/livrer")
    public CommandeFournisseurDTO livrerCommande(@PathVariable Long commandeId) {
        return commandeFournisseurService.deliverCommande(commandeId);
    }


    @PutMapping("/{id}/produits")
    public ResponseEntity<?> updateProduits(
            @PathVariable Long id,
            @RequestBody List<ProduitQuantiteDTO> produitsMaj) {
        try {
            CommandeFournisseurDTO updated = commandeFournisseurService.updateCommandeProduits(id, produitsMaj);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
