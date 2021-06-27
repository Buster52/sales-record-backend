package com.buster.backend.controllers;

import com.buster.backend.dto.CategoriaDTO;
import com.buster.backend.service.CategoriaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.save(categoriaDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> ObtenerCategorias() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriaService.getCategoria(id));
    }
}