package com.buster.backend.controllers;

import com.buster.backend.dto.EntradaDto;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.EntradaService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> createEntrada(@RequestBody EntradaDto entradaDto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            entradaService.save(entradaDto);
            return new ResponseEntity<>("Guardado correctamente", HttpStatus.CREATED);
        } catch (NotFoundException e) {
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(entradaService.getAll());
    }

}