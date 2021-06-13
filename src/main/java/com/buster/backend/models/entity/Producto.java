package com.buster.backend.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Id
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombre;

    private String descripcion;

    private Integer cantidad;

    private Double precio;
}
