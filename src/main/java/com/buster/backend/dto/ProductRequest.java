package com.buster.backend.dto;

public class ProductRequest {
    private Long productId;
    private String productName;
    private String description;
    private String category;
    private Integer amount;
    private Double price;

    public ProductRequest() {
    }

    public ProductRequest(Long productId, String productName, String description, String category, Integer amount, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
