package com.buster.backend.dto;

public class EntradaDto {
    private Long id;
    private String description;
    private String productName;
    private Integer amount;
    private String userName;
    private String date;

    public EntradaDto() {
    }

    public EntradaDto(Long id, String description, String productName, Integer amount, String userName, String date) {
        this.id = id;
        this.description = description;
        this.productName = productName;
        this.amount = amount;
        this.userName = userName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}