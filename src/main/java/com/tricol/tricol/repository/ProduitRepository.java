package com.tricol.tricol.repository;

import com.tricol.tricol.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

     Produit findByNom(String nom);
}
