package com.buster.backend.models.dao;

import com.buster.backend.model.Producto;
import org.springframework.data.repository.CrudRepository;

public interface IProductDao extends CrudRepository<Producto, Long> {
}
