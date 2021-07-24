package com.buster.backend.controllers;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.CategoriaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            categoriaService.save(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Agregado exitosamente");
        } catch (AlreadyExistsException alreadyExistsException) {
            resp.put("error", alreadyExistsException.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(categoriaService.getCategoryById(id));
        } catch (NotFoundException notFoundException) {
            resp.put("error", notFoundException.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        }
    }
}