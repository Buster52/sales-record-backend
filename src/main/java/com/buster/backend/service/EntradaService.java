package com.buster.backend.service;

import com.buster.backend.dto.EntradaDto;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.mapper.EntradaMapper;
import com.buster.backend.model.Entrada;
import com.buster.backend.model.Producto;
import com.buster.backend.repository.EntradaRepository;
import com.buster.backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EntradaService {

    private final ProductRepository productRepository;
    private final EntradaRepository entradaRepository;
    private final EntradaMapper entradaMapper;
    private final AuthService authService;

    @Transactional
    public EntradaDto save(EntradaDto entradaDto) {
        Optional<Producto> producto = productRepository.findByName(entradaDto.getProductName());
        if (producto.isEmpty()) {
            throw new NotFoundException("No existe el producto con el nombre - " + entradaDto.getProductName());
        } else {
            Entrada entrada = entradaRepository.save(entradaMapper.map(entradaDto, producto.get(), authService.getCurrentUser()));
            entradaDto.setId(entrada.getId());
            producto.get().setAmount(producto.get().getAmount() + entradaDto.getAmount());
            productRepository.save(producto.get());
        }
        return entradaDto;
    }

    public List<EntradaDto> getAll() {
        return entradaRepository.findAll()
                .stream()
                .map(entradaMapper::mapToDto)
                .collect(Collectors.toList());
    }
}