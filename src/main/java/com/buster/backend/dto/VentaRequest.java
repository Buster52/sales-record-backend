package com.buster.backend.dto;

import java.time.Instant;

public class VentaRequest {
    private Long saleId;

    private String clientName;

    private String productName;

    private Long amount;

    private Double pay;

    public VentaRequest() {
    }

    public VentaRequest(Long saleId, String clientName, String productName, Long amount, Double pay) {
        this.saleId = saleId;
        this.clientName = clientName;
        this.productName = productName;
        this.amount = amount;
        this.pay = pay;
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
}
