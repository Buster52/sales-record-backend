package com.buster.backend.repository;

import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByName(String category) throws NotFoundException;
}