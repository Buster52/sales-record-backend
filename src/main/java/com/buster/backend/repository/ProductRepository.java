package com.buster.backend.repository;

import com.buster.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Producto, Long> {
}
