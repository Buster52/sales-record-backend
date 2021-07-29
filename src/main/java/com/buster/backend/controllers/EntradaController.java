package com.buster.backend.controllers;

import com.buster.backend.common.ApiResponse;
import com.buster.backend.dto.EntradaDto;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.service.EntradaService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/entradas")
@AllArgsConstructor
public class EntradaController {
    @Autowired
    private EntradaService entradaService;

    @PostMapping
    public ResponseEntity<ApiResponse> createEntrada(@RequestBody EntradaDto entradaDto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            entradaService.save(entradaDto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", "Creado exitosamente", 201));
        } catch (NotFoundException e) {
            resp.put("message", e.getMessage());
	    resp.put("code", e.getCode());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Ha ocurrido un error", 404, resp));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
	List<EntradaDto> entradasList = entradaService.getAll();

	Map<String, Object> data = new HashMap<>();
	data.put("entradas", entradasList);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", data));
    }
}
