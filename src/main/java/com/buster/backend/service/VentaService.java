package com.buster.backend.service;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.dto.VentaResponse;
import com.buster.backend.exceptions.CustomException;
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
        if (producto.get().getAmount() < 1) {
            throw new CustomException("No hay unidades disponibles");
        } else {
            Venta ventaMapped = ventaMapper.map(ventaRequest, producto.get(), authService.getCurrentUser());
            ventaRepository.save(ventaMapped);
            producto.get().setAmount(producto.get().getAmount() - ventaRequest.getAmount());
            productRepository.save(producto.get());
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
    public void updatePay(Double pay, Long id) {
        Venta venta = ventaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe venta con id - " + id)
        );
        if (pay > venta.getBalance()) {
            throw new CustomException("El pago no puede superar el balance");
        }
        Double totalPay = venta.getPay() + pay;
        Double balance = venta.getTotal() - totalPay;
        venta.setPay(totalPay);
        venta.setBalance(balance);
        ventaRepository.save(venta);
    }
}