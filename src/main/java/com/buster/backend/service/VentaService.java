package com.buster.backend.service;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.dto.VentaResponse;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.mapper.VentaMapper;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Venta;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.repository.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class VentaService {

    private final ProductRepository productRepository;
    private final VentaRepository ventaRepository;
    private final AuthService authService;
    private final VentaMapper ventaMapper;

    @Transactional
    public void save(VentaRequest ventaRequest) {
        Producto producto = productRepository.findByName(ventaRequest.getProductName());
        if (producto != null && producto.getAmount() > 0) {
            Venta venta = ventaRepository.save(ventaMapper.map(ventaRequest, producto, authService.getCurrentUser()));
            ventaRequest.setSaleId(venta.getSaleId());
        }
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> getAll() {
        return ventaRepository.findAll()
                .stream()
                .map(ventaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePay(Long id, VentaRequest ventaRequest) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new CustomException("No existe venta con id - " + id));
        Producto producto = productRepository.findByName(ventaRequest.getProductName());
        if (producto != null) {
            venta.setPay(ventaRequest.getPay());
            ventaRepository.save(ventaMapper.map(ventaRequest, producto, authService.getCurrentUser()));
        }
    }
}