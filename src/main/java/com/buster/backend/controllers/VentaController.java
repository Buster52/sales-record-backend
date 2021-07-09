package com.buster.backend.controllers;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.model.Producto;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
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
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody VentaRequest ventaRequest) {
        Map<String, Object> resp = new HashMap<>();
        Producto productoActual = productRepository.findByName(ventaRequest.getProductName());
        try {
            ventaService.save(ventaRequest);
        } catch (DataAccessException e) {
            resp.put("message", "Ha ocurrido un error.");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        productoActual.setAmount(productoActual.getAmount() - ventaRequest.getAmount());
        productRepository.save(productoActual);
        resp.put("message", "Agregado exitosamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.getAll());
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePay(@PathVariable Long id, @RequestBody VentaRequest ventaRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {
            ventaService.updatePay(id, ventaRequest);
        } catch (Exception e) {
            resp.put("message", "Ha ocurrido un error");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("message", "Pago actualizado correctamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }
}