package com.buster.backend.repository;

import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAllByCategoria(Categoria categoria);

    List<Producto> findByUsuario(Usuario usuario);

    Producto findByName(String productName);
}
