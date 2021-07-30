package com.buster.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.buster.backend.common.ApiResponse;
import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categorias")
@AllArgsConstructor
@Slf4j
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            categoriaService.save(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", "Agregado exitosamente",201,null));
        } catch (AlreadyExistsException alreadyExistsException) {
            resp.put("message", alreadyExistsException.getMessage());
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", 400,  resp));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
      List<CategoryResponse> categoryList = categoriaService.getAll();

      Map<String,Object> data = new HashMap<>();
      data.put("categories", categoryList);

      return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
	    CategoryResponse category = categoriaService.getCategoryById(id);
	    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", category));
        } catch (NotFoundException notFoundException) {
            resp.put("message", notFoundException.getMessage());
	    resp.put("code", notFoundException.getCode());
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", 500, resp));
        }
    }
}
