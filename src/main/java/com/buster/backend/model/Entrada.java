package com.buster.backend.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "entradas")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Usuario usuario;

    @NotNull
    @Min(value = 1, message = "La cantidad minima es 1")
    private int amount;

    private Instant date;

    public Entrada() {
    }

    public Entrada(Long id, String description, Producto producto, Usuario usuario, int amount, Instant date) {
        this.id = id;
        this.description = description;
        this.producto = producto;
        this.usuario = usuario;
        this.amount = amount;
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}