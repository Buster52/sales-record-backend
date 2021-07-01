package com.buster.backend.dto;

import java.time.Instant;

public class VentaDto {
    private Long saleId;

    private String clientName;

    private String productName;

    private Long amount;

    private Double pay;

    private Double balance;

    private Double total;

    private String userName;

    private Instant date;

    public VentaDto() {
    }

    public VentaDto(Long saleId, String clientName, String productName, Long amount, Double pay, Double balance, Double total, String userName, Instant date) {
        this.saleId = saleId;
        this.clientName = clientName;
        this.productName = productName;
        this.amount = amount;
        this.pay = pay;
        this.balance = balance;
        this.total = total;
        this.userName = userName;
        this.date = date;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
