package com.buster.backend.controllers;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.model.Categoria;
import com.buster.backend.service.CategoriaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoryRequest> crearCategoria(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoriaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.getCategoria(id));
    }
}