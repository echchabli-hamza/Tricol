package com.tricol.tricol.repository;

import com.tricol.tricol.entity.Fournisseur;
import com.tricol.tricol.entity.MouvementStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

}
