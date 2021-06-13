package com.buster.backend.models.services;

import java.awt.print.Pageable;
import java.util.List;

public interface IBaseService<T> {
    public List<T> findAll(Pageable pageable);

    public T findById(Long id);

    public T save(T t);

    public void delete(Long id);
}