package com.buster.backend.controllers;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
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
    public ResponseEntity<?> crearCategoria(@RequestBody CategoryRequest categoryRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            categoriaService.save(categoryRequest);
        } catch (DataAccessException e) {
            resp.put("message", "Ha ocurrido un error.");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("message", "Categoria agregada exitosamente");
        resp.put("categoria", categoryRequest);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> ObtenerCategorias() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.getAll());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryResponse> getCategoria(@PathVariable Long id) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(categoriaService.getCategoria(id));
//    }
}