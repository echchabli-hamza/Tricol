package com.tricol.tricol.service.impl;

import com.tricol.tricol.dto.FournisseurDTO;
import com.tricol.tricol.entity.Fournisseur;
import com.tricol.tricol.mapper.FournisseurMapper;
import com.tricol.tricol.repository.FournisseurRepository;
import com.tricol.tricol.service.FournisseurService;
import com.tricol.tricol.service.MyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;
    private final MyService myService;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository,
                                  FournisseurMapper fournisseurMapper,
                                  MyService myService) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurMapper = fournisseurMapper;
        this.myService = myService;
    }

    @Override
    public FournisseurDTO save(FournisseurDTO dto) {
        // Optional logging / service call
        myService.doSomething(dto.toString());

        // Convert DTO → Entity
        Fournisseur entity = fournisseurMapper.toEntity(dto);

        // Save to DB
        Fournisseur saved = fournisseurRepository.save(entity);

        // Convert Entity → DTO
        return fournisseurMapper.toDto(saved);
    }

    @Override
    public FournisseurDTO findById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found with id " + id));
        return fournisseurMapper.toDto(fournisseur);
    }

    @Override
    public List<FournisseurDTO> findAll() {
        return fournisseurRepository.findAll()
                .stream()
                .map(fournisseurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new RuntimeException("Fournisseur not found with id " + id);
        }
        fournisseurRepository.deleteById(id);
    }
}
