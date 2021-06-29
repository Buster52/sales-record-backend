package com.buster.backend.dto;

import lombok.Builder;

@Builder
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private Integer numberOfProducts;
    private String userName;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String name, Integer numberOfProducts, String userName) {
        this.id = id;
        this.categoryName = name;
        this.numberOfProducts = numberOfProducts;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(Integer numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}