package com.buster.backend.models.services;

import com.buster.backend.models.entity.Producto;

import java.awt.print.Pageable;
import java.util.List;

public class ProductoServiceImpl implements IBaseService<Producto> {
    @Override
    public List<Producto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Producto findById(Long id) {
        return null;
    }

    @Override
    public Producto save(Producto m) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}