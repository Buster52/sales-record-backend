package com.buster.backend.models.dao;

import com.buster.backend.model.TipoProducto;
import org.springframework.data.repository.CrudRepository;

public interface ITipoProductoDao extends CrudRepository<TipoProducto, Long> {
}
