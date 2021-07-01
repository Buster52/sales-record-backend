package com.buster.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "entradas")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Producto product;

    @NotNull
    private int amount;

    @NotNull
    private Instant date;
}