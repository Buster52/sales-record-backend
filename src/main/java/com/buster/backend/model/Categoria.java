package com.buster.backend.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Producto> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Usuario usuario;

    public Categoria(Long categoryId, String name, List<Producto> products, Usuario usuario) {
        this.categoryId = categoryId;
        this.name = name;
        this.products = products;
        this.usuario = usuario;
    }

    public Categoria() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Producto> getProducts() {
        return products;
    }

    public void setProducts(List<Producto> products) {
        this.products = products;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}