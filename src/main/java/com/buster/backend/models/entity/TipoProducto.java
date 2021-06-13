package com.buster.backend.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipo_producto")
public class TipoProducto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipo_producto")
    private Long idTipoProducto;

    @Column(name = "nombre_tipo")
    private String nombreTipo;

}
