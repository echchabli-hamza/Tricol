package com.tricol.tricol.repository;

import com.tricol.tricol.entity.CommandeFournisseur;
import com.tricol.tricol.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Long>, JpaSpecificationExecutor<CommandeFournisseur> {
}
