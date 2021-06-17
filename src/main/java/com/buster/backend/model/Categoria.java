package com.buster.backend.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class TipoProducto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreTipo;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Producto> productos;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
}