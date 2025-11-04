package com.tricol.tricol.dto;


import java.sql.Date;

public class MouvementStockDTO {
    private Long id;
    private Long produitId;
    private String type;
    private Integer quantity;
    private Date datemovements;


    private Long commandeFournisseurId;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduitId() {
        return produitId;
    }
    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }



    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantite) {
        this.quantity = quantite;
    }

    public Date getDatemovements() {
        return datemovements;
    }

    public void setDatemovements(Date  datemovements) {
        this.datemovements = datemovements;
    }

    public Long getCommandeFournisseurId() {
        return commandeFournisseurId;
    }
    public void setCommandeFournisseurId(Long commandeFournisseurId) {
        this.commandeFournisseurId = commandeFournisseurId;
    }
}
