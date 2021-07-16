package com.buster.backend.service;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.dto.VentaResponse;
import com.buster.backend.exceptions.NotFoundException;
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
import java.util.Optional;
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
        Optional<Producto> producto = productRepository.findByName(ventaRequest.getProductName());
        if (producto.isEmpty()) {
            throw new NotFoundException("No existe ese producto.");
        }
        if (producto.get().getAmount() > 0) {
            Venta venta = ventaRepository.save(ventaMapper.map(ventaRequest, producto.get(), authService.getCurrentUser()));
            ventaRequest.setSaleId(venta.getSaleId());
        }
        producto.get().setAmount(producto.get().getAmount() - ventaRequest.getAmount());
        productRepository.save(producto.get());
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> getAll() {
        return ventaRepository.findAll()
                .stream()
                .map(ventaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePay(VentaRequest ventaRequest, Long id) {
        Venta venta = ventaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe venta con id - " + id)
        );
        Optional<Producto> producto = productRepository.findByName(ventaRequest.getProductName());
        if (producto.isPresent()) {
            Double totalPay = venta.getPay() + ventaRequest.getPay();
            Double balance = venta.getBalance() - ventaRequest.getPay();
            venta.setPay(totalPay);
            venta.setBalance(balance);
            ventaRepository.save(venta);
        }
    }
}