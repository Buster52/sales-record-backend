package com.buster.backend.dto;

import lombok.Builder;

@Builder
public class CategoriaDTO {
    private Long id;
    private String name;
    private Integer numberOfProducts;

    public CategoriaDTO(Long id, String name, Integer numberOfProducts) {
        this.id = id;
        this.name = name;
        this.numberOfProducts = numberOfProducts;
    }

    public CategoriaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(Integer numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }
}