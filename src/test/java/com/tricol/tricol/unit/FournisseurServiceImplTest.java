package com.tricol.tricol.unit;


import com.tricol.tricol.dto.FournisseurDTO;
import com.tricol.tricol.entity.Fournisseur;
import com.tricol.tricol.mapper.FournisseurMapper;
import com.tricol.tricol.repository.FournisseurRepository;
import com.tricol.tricol.service.MyService;
import com.tricol.tricol.service.impl.FournisseurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FournisseurServiceImplTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private FournisseurMapper fournisseurMapper;

    @Mock
    private MyService myService;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testSave_success() {

        FournisseurDTO dto = new FournisseurDTO();
        dto.setContact("ACME SARL");

        Fournisseur entity = new Fournisseur();
        entity.setId(1L);

        Fournisseur savedEntity = new Fournisseur();
        savedEntity.setId(1L);
        savedEntity.setContact("ACME SARL");

        FournisseurDTO returnedDto = new FournisseurDTO();
        returnedDto.setId(1L);
        returnedDto.setContact("ACME SARL");

        when(fournisseurMapper.toEntity(dto)).thenReturn(entity);
        when(fournisseurRepository.save(entity)).thenReturn(savedEntity);
        when(fournisseurMapper.toDto(savedEntity)).thenReturn(returnedDto);

        FournisseurDTO result = fournisseurService.save(dto);

        assertNotNull(result);
        assertEquals("ACME SARL", result.getContact());

        verify(myService, times(1)).doSomething(dto.toString());
        verify(fournisseurMapper).toEntity(dto);
        verify(fournisseurRepository).save(entity);
        verify(fournisseurMapper).toDto(savedEntity);
    }


    @Test
    void testFindById_success() {

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);

        FournisseurDTO dto = new FournisseurDTO();
        dto.setId(1L);

        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));
        when(fournisseurMapper.toDto(fournisseur)).thenReturn(dto);

        FournisseurDTO result = fournisseurService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(fournisseurRepository).findById(1L);
        verify(fournisseurMapper).toDto(fournisseur);
    }

    @Test
    void testFindById_notFound() {
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> fournisseurService.findById(1L));
    }


    @Test
    void testFindAll_pageable() {

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);

        FournisseurDTO dto = new FournisseurDTO();
        dto.setId(1L);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Fournisseur> page = new PageImpl<>(List.of(fournisseur));

        when(fournisseurRepository.findAll(pageable)).thenReturn(page);
        when(fournisseurMapper.toDto(fournisseur)).thenReturn(dto);

        Page<FournisseurDTO> result = fournisseurService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        verify(fournisseurRepository).findAll(pageable);
    }


    @Test
    void testDelete_success() {

        when(fournisseurRepository.existsById(1L)).thenReturn(true);

        fournisseurService.delete(1L);

        verify(fournisseurRepository).deleteById(1L);
    }

    @Test
    void testDelete_notFound() {

        when(fournisseurRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> fournisseurService.delete(1L));
    }
}
