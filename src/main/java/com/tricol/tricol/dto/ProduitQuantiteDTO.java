package com.tricol.tricol.dto;


public class ProduitQuantiteDTO {
    private Long produitId;
    private Integer quantite;

    public ProduitQuantiteDTO(long l, int i) {
        this.produitId = l;
        this.quantite = i;
    }
    public  ProduitQuantiteDTO() {

    }


    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}

