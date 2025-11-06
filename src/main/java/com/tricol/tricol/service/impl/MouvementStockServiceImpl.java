package com.tricol.tricol.service.impl;

import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.mapper.MouvementStockMapper;
import com.tricol.tricol.repository.MouvementStockRepository;
import com.tricol.tricol.repository.ProduitRepository;
import com.tricol.tricol.service.MouvementStockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MouvementStockServiceImpl implements MouvementStockService {

    private final MouvementStockRepository mouvementStockRepository;
    private final ProduitRepository produitRepository;
    private final MouvementStockMapper mouvementStockMapper;

    public MouvementStockServiceImpl(MouvementStockRepository mouvementStockRepository,
                                     ProduitRepository produitRepository,
                                     MouvementStockMapper mouvementStockMapper) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.produitRepository = produitRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    @Override
    @Transactional
    public MouvementStockDTO save(MouvementStockDTO dto) {
        Produit produit = produitRepository.findById(dto.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit not found"));


        MouvementStock mouvement = mouvementStockMapper.toEntity(dto);


        MouvementStock saved = mouvementStockRepository.save(mouvement);
        return mouvementStockMapper.toDto(saved);
    }

    @Override
    public MouvementStockDTO findById(Long id) {
        MouvementStock mouvement = mouvementStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MouvementStock not found"));
        return mouvementStockMapper.toDto(mouvement);
    }

    @Override
    public List<MouvementStockDTO> findAll() {
        return mouvementStockRepository.findAll().stream()
                .map(mouvementStockMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MouvementStockDTO> findAllM(Pageable pageable) {
        return mouvementStockRepository.findAll(pageable)
                .map(mouvementStockMapper::toDto);
    }


    @Override
    public List<MouvementStockDTO> findByProduitId(Long produitId) {
        return mouvementStockRepository.findByProduitId(produitId).stream()
                .map(mouvementStockMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        mouvementStockRepository.deleteById(id);
    }
}
