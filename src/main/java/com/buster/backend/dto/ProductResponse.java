package com.buster.backend.dto;

public class ProductResponse {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Long amount;
    private String category;
    private String userName;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String productName, String description, Double price, Long amount, String userName, String category) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.userName = userName;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}