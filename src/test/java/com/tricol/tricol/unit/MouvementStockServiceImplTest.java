package com.tricol.tricol.unit;

import  com.tricol.tricol.service.impl.MouvementStockServiceImpl;
import com.tricol.tricol.dto.MouvementStockDTO;
import com.tricol.tricol.entity.MouvementStock;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.entity.TypeMouvement;
import com.tricol.tricol.mapper.MouvementStockMapper;
import com.tricol.tricol.repository.MouvementStockRepository;
import com.tricol.tricol.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MouvementStockServiceImplTest {

    @Mock
    private MouvementStockRepository mouvementStockRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private MouvementStockMapper mouvementStockMapper;

    @InjectMocks
    private MouvementStockServiceImpl mouvementStockService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_success() {
        MouvementStockDTO dto = new MouvementStockDTO();
        dto.setProduitId(1L);

        Produit produit = new Produit();
        produit.setId(1L);

        MouvementStock mouvement = new MouvementStock();
        mouvement.setId(10L);

        MouvementStock saved = new MouvementStock();
        saved.setId(10L);

        MouvementStockDTO returnedDto = new MouvementStockDTO();
        returnedDto.setId(10L);

        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(mouvementStockMapper.toEntity(dto)).thenReturn(mouvement);
        when(mouvementStockRepository.save(mouvement)).thenReturn(saved);
        when(mouvementStockMapper.toDto(saved)).thenReturn(returnedDto);

        MouvementStockDTO result = mouvementStockService.save(dto);

        assertEquals(10L, result.getId());
        verify(produitRepository).findById(1L);
        verify(mouvementStockRepository).save(mouvement);
    }

    @Test
    void save_produitNotFound() {
        MouvementStockDTO dto = new MouvementStockDTO();
        dto.setProduitId(1L);

        when(produitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> mouvementStockService.save(dto));
    }

    @Test
    void findById_success() {
        MouvementStock mouvement = new MouvementStock();
        mouvement.setId(5L);

        MouvementStockDTO dto = new MouvementStockDTO();
        dto.setId(5L);

        when(mouvementStockRepository.findById(5L)).thenReturn(Optional.of(mouvement));
        when(mouvementStockMapper.toDto(mouvement)).thenReturn(dto);

        MouvementStockDTO result = mouvementStockService.findById(5L);

        assertEquals(5L, result.getId());
    }

    @Test
    void findById_notFound() {
        when(mouvementStockRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> mouvementStockService.findById(5L));
    }

    @Test
    void findAll_success() {
        MouvementStock m = new MouvementStock();
        MouvementStockDTO dto = new MouvementStockDTO();

        when(mouvementStockRepository.findAll()).thenReturn(List.of(m));
        when(mouvementStockMapper.toDto(m)).thenReturn(dto);

        List<MouvementStockDTO> result = mouvementStockService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findAllM_success() {
        MouvementStock m = new MouvementStock();
        MouvementStockDTO dto = new MouvementStockDTO();

        Page<MouvementStock> page = new PageImpl<>(List.of(m));
        Pageable pageable = PageRequest.of(0, 10);

        when(mouvementStockRepository.findAll(pageable)).thenReturn(page);
        when(mouvementStockMapper.toDto(m)).thenReturn(dto);

        Page<MouvementStockDTO> result = mouvementStockService.findAllM(pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findByProduitId_success() {
        MouvementStock m = new MouvementStock();
        MouvementStockDTO dto = new MouvementStockDTO();

        when(mouvementStockRepository.findByProduitId(1L)).thenReturn(List.of(m));
        when(mouvementStockMapper.toDto(m)).thenReturn(dto);

        List<MouvementStockDTO> result = mouvementStockService.findByProduitId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void filter_nullState() {
        MouvementStock m = new MouvementStock();
        MouvementStockDTO dto = new MouvementStockDTO();

        when(mouvementStockRepository.findAll()).thenReturn(List.of(m));
        when(mouvementStockMapper.toDto(m)).thenReturn(dto);

        List<MouvementStockDTO> result = mouvementStockService.filter(null);

        assertEquals(1, result.size());
    }

    @Test
    void filter_stateProvided() {
        MouvementStock m = new MouvementStock();
        MouvementStockDTO dto = new MouvementStockDTO();


        when(mouvementStockRepository.findAll(ArgumentMatchers.<Specification<MouvementStock>>any()))
                .thenReturn(List.of(m));


        when(mouvementStockMapper.toDto(m)).thenReturn(dto);


        List<MouvementStockDTO> result = mouvementStockService.filter(TypeMouvement.ENTREE);


        assertEquals(1, result.size());
        assertSame(dto, result.get(0));


        verify(mouvementStockRepository, times(1))
                .findAll(ArgumentMatchers.<Specification<MouvementStock>>any());


        verify(mouvementStockRepository, never()).findAll();
    }


    @Test
    void delete_success() {
        mouvementStockService.delete(1L);
        verify(mouvementStockRepository).deleteById(1L);
    }
}
