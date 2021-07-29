package com.buster.backend.controllers;

import com.buster.backend.common.ApiResponse;
import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/productos")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            productService.save(productRequest);
	    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", "Agregado exitosamente", 201));
        } catch (AlreadyExistsException e) {
            resp.put("message", e.getMessage());
	    resp.put("code", e.getCode());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
	} catch (NotFoundException notFoundException){
            resp.put("message", notFoundException.getMessage());
	    resp.put("code", notFoundException.getCode());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
	}
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
	List<ProductResponse> productos = productService.getAllProducts();

	Map<String, Object> resp = new HashMap<>();
	resp.put("productos", productos);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", resp));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("success",productService.getProductById(id)));
        } catch (NotFoundException notFoundException) {
            resp.put("message", notFoundException.getMessage());
	    resp.put("code", notFoundException.getCode());
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductsByCategoria(@PathVariable String categoryName) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("success",productService.getProductByCategoria(categoryName)));
        } catch (NotFoundException notFoundException) {
            resp.put("message", notFoundException.getMessage());
            resp.put("code", notFoundException.getCode());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
        }
    }

    @GetMapping("/username/{name}")
    public ResponseEntity<ApiResponse> getProductsByUsername(@PathVariable String name) {
        Map<String, Object> resp = new HashMap<>();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("success",productService.getProductsByUsername(name)));
        } catch (NotFoundException notFoundException) {
            resp.put("message", notFoundException.getMessage());
            resp.put("code", notFoundException.getCode());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("error", "Ha ocurrido un error", resp));
        }
    }
}
