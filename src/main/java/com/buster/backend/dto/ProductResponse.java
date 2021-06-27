package com.buster.backend.dto;

public class ProductResponse {
    private Long id;
    private String productName;
    private String description;
    private String userName;
    private String category;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String productName, String description, String userName, String category) {
        this.id = id;
        this.productName = productName;
        this.description = description;
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