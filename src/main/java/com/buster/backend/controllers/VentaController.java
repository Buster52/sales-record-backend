package com.buster.backend.controllers;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.model.Venta;
import com.buster.backend.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
public class VentaController {
    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<?> createVenta(@RequestBody VentaRequest ventaRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            ventaService.save(ventaRequest);
        } catch (NotFoundException e) {
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("message", "Agregado exitosamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePay(@RequestBody VentaRequest ventaRequest, @PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            ventaService.updatePay(ventaRequest, id);
        } catch (NotFoundException e) {
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("message", "Pago actualizado correctamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }
}