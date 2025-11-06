package com.tricol.tricol.dto;

public class ProductStockReportDTO {
    private Long produitId;
    private int totalPurchasedUnits;
    private double totalPurchasedCost;
    private double averagePrice;
    private int totalSoldUnits;
    private double totalRevenue;

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public double getTotalPurchasedCost() {
        return totalPurchasedCost;
    }

    public void setTotalPurchasedCost(double totalPurchasedCost) {
        this.totalPurchasedCost = totalPurchasedCost;
    }

    public int getTotalPurchasedUnits() {
        return totalPurchasedUnits;
    }

    public void setTotalPurchasedUnits(int totalPurchasedUnits) {
        this.totalPurchasedUnits = totalPurchasedUnits;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalSoldUnits() {
        return totalSoldUnits;
    }

    public void setTotalSoldUnits(int totalSoldUnits) {
        this.totalSoldUnits = totalSoldUnits;
    }
}
