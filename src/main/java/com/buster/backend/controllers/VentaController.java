package com.buster.backend.controllers;

import com.buster.backend.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import com.buster.backend.common.ApiResponse;
import com.buster.backend.dto.VentaRequest;
import com.buster.backend.dto.VentaResponse;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.VentaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ventas")
@AllArgsConstructor
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<ApiResponse> createVenta(@RequestBody VentaRequest ventaRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            ventaService.save(ventaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", "Agregado exitosamente"));
        } catch (NotFoundException e) {
            resp.put("message", e.getMessage());
            resp.put("code", e.getCode());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<VentaResponse> ventaList = ventaService.getAll();

        Map<String, Object> data = new HashMap<>();
        data.put("ventas", ventaList);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", data));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> updatePay(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Map<String, Object> resp = new HashMap<>();
        try {
            ventaService.updatePay(id, fields);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", "Actualizado correctamente"));
        } catch (NotFoundException e) {
            resp.put("message", e.getMessage());
            resp.put("code", e.getCode());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
        } catch (CustomException e){
            resp.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", resp));
        }
    }
}