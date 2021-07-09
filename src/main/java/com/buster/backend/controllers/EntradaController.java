package com.buster.backend.controllers;

import com.buster.backend.dto.EntradaDto;
import com.buster.backend.dto.VentaRequest;
import com.buster.backend.model.Producto;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.service.EntradaService;
import com.buster.backend.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/entradas")
@AllArgsConstructor
public class EntradaController {
    private final EntradaService entradaService;

    @PostMapping
    public ResponseEntity<?> crearEntrada(@RequestBody EntradaDto entradaDto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            entradaService.save(entradaDto);
        } catch (Exception e) {
            resp.put("message", "Ha ocurrido un error.");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("message", "Creado exitosamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(entradaService.getAll());
    }

}