package com.buster.backend.controllers;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            productService.save(productRequest);
        } catch (NotFoundException | AlreadyExistsException e) {
            resp.put("message", "Ha ocurrido un error.");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("message", "Agregado exitosamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productService.getProduct(id));
        } catch (NotFoundException notFoundException) {
            resp.put("error", notFoundException.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("by-categoria/{id}")
    public ResponseEntity<?> getProductsByCategoria(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productService.getProductByCategoria(id));
        } catch (NotFoundException notFoundException) {
            resp.put("error", notFoundException.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<?> getProductsByUsername(@PathVariable String name) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productService.getProductsByUsername(name));
        } catch (NotFoundException notFoundException) {
            resp.put("error", notFoundException.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        }
    }
}