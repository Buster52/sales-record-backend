package com.buster.backend.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ventas")
public class Venta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    private String descripcion;

    private Long cantidad;

    private Double total;
}
