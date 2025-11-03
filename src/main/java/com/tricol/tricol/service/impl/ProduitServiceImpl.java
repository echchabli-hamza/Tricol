package  com.tricol.tricol.service.impl;

import com.tricol.tricol.dto.ProductCostDTO;
import com.tricol.tricol.dto.ProduitDTO;
import com.tricol.tricol.dto.ProduitStockDTO;
import com.tricol.tricol.entity.ProductCost;
import com.tricol.tricol.entity.Produit;
import com.tricol.tricol.mapper.ProductCostMapper;
import com.tricol.tricol.mapper.ProduitMapper;
import com.tricol.tricol.repository.ProductCostRepository;
import com.tricol.tricol.repository.ProduitRepository;
import com.tricol.tricol.service.ProduitService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final ProductCostRepository productCostRepository;
    private final ProduitMapper produitMapper ;

    private final ProductCostMapper productCostMapper ;


    public ProduitServiceImpl(ProduitRepository produitRepository,
                              ProductCostRepository productCostRepository , ProduitMapper produitMapper, ProductCostMapper productCostMapper) {
        this.produitRepository = produitRepository;
        this.productCostRepository = productCostRepository;
        this.produitMapper = produitMapper;
        this.productCostMapper = productCostMapper;
    }

    @Override
    @Transactional
    public ProduitStockDTO save(ProduitStockDTO dto) {

        Produit produit = produitRepository.findByNom(dto.getNom());
        if (produit == null) {
            produit = new Produit();
            produit.setNom(dto.getNom());
            produit.setDescription(dto.getDescription());
            produit.setCategorie(dto.getCategorie());
            produit = produitRepository.save(produit);
        }


        if (dto.getTotalUnits() != null && dto.getTotalUnits() > 0) {
            ProductCost cost = new ProductCost();
            cost.setProduit(produit);
            cost.setPrixUnitaire(dto.getPrixUnitaire());
            cost.setTotalUnits(dto.getTotalUnits());
            cost.setRemainingUnits(dto.getRemainingUnits() != null ? dto.getRemainingUnits() : dto.getTotalUnits());
            productCostRepository.save(cost);
        }

        return buildProduitStockDTO(produit);
    }

    @Override
    public ProduitDTO findById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));


        ProduitDTO dto = produitMapper.toDto(produit);


        List<ProductCostDTO> costs = produit.getCosts().stream()
                .map(productCostMapper::toDto)
                .collect(Collectors.toList());
        dto.setCosts(costs);


        int stockTotal = costs.stream()
                .mapToInt(ProductCostDTO::getRemainingUnits)
                .sum();
        dto.setStockTotal(stockTotal);

        return dto;
    }

    @Override
    public List<ProduitDTO> findAll() {
        return produitRepository.findAll().stream().map(produit -> {
            // Map basic product fields
            ProduitDTO dto = produitMapper.toDto(produit);

            // Map product costs
            List<ProductCostDTO> costs = produit.getCosts().stream()
                    .map(productCostMapper::toDto)
                    .collect(Collectors.toList());
            dto.setCosts(costs);

            // Compute stock total
            int stockTotal = costs.stream()
                    .mapToInt(ProductCostDTO::getRemainingUnits)
                    .sum();
            dto.setStockTotal(stockTotal);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productCostRepository.findByProduitId(id).forEach(productCostRepository::delete);
        produitRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProduitStockDTO update(Long id, ProduitStockDTO dto) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));

        produit.setNom(dto.getNom());
        produit.setDescription(dto.getDescription());
        produit.setCategorie(dto.getCategorie());
        produitRepository.save(produit);

        if (dto.getTotalUnits() != null && dto.getTotalUnits() > 0) {
            ProductCost cost = new ProductCost();
            cost.setProduit(produit);
            cost.setPrixUnitaire(dto.getPrixUnitaire());
            cost.setTotalUnits(dto.getTotalUnits());
            cost.setRemainingUnits(dto.getRemainingUnits() != null ? dto.getRemainingUnits() : dto.getTotalUnits());
            productCostRepository.save(cost);
        }

        return buildProduitStockDTO(produit);
    }


    private ProduitStockDTO buildProduitStockDTO(Produit produit) {
        ProduitStockDTO dto = new ProduitStockDTO();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setDescription(produit.getDescription());
        dto.setCategorie(produit.getCategorie());

        int totalUnits = 0;
        int remainingUnits = 0;
        double lastPrice = 0.0;

        if (produit.getCosts() != null) {
            for (ProductCost cost : produit.getCosts()) {
                totalUnits += cost.getTotalUnits();
                remainingUnits += cost.getRemainingUnits();
                lastPrice = cost.getPrixUnitaire();
            }
        }

        dto.setTotalUnits(totalUnits);
        dto.setRemainingUnits(remainingUnits);
        dto.setPrixUnitaire(lastPrice);

        return dto;
    }
}
