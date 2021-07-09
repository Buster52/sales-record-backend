package com.buster.backend.dto;

import java.time.Instant;

public class VentaResponse {
    private Long id;

    private String clientName;

    private String productName;

    private Long amount;

    private Double pay;

    private Double balance;

    private Double total;

    private String userName;

    private String date;

    public VentaResponse() {
    }

    public VentaResponse(Long id, String clientName, String productName, Long amount, Double pay, Double balance, Double total, String userName, String date) {
        this.id = id;
        this.clientName = clientName;
        this.productName = productName;
        this.amount = amount;
        this.pay = pay;
        this.balance = balance;
        this.total = total;
        this.userName = userName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
