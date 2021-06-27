package com.buster.backend.controllers;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.save(productRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> obtenerTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProduct(id));
    }

    @GetMapping("by-categoria/{id}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoria(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProductByCategoria(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<ProductResponse>> getProductsByUsername(@PathVariable String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProductsByUsername(name));
    }
}