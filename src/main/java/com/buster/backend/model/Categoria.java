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
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Producto> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Usuario user;

    public Categoria(Long id, String name, List<Producto> products, Usuario user) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.user = user;
    }

    public Categoria() {
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

    public List<Producto> getProducts() {
        return products;
    }

    public void setProducts(List<Producto> products) {
        this.products = products;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}