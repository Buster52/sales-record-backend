package com.buster.backend.repository;

import com.buster.backend.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
}
