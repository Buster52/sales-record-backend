package com.buster.backend.mapper;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.dto.VentaResponse;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.model.Venta;
import com.buster.backend.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public abstract class VentaMapper {

    @Autowired
    private ProductRepository productoRepo;

    @Mapping(target = "clientName", source = "ventaRequest.clientName")
    @Mapping(target = "amount", source = "ventaRequest.amount")
    @Mapping(target = "pay", source = "ventaRequest.pay")
    @Mapping(target = "balance", expression = "java( getTotal(ventaRequest, false))")
    @Mapping(target = "total", expression = "java(getTotal(ventaRequest, true))")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "producto", source = "producto")
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    public abstract Venta map(VentaRequest ventaRequest, Producto producto, Usuario usuario);

    Double getTotal(VentaRequest ventaRequest, boolean isTotal) {
        Producto producto = productoRepo.findByName(ventaRequest.getProductName());
        Double totalPay = ventaRequest.getAmount() * producto.getPrice();
        Double balance = totalPay - ventaRequest.getPay();

        if (!isTotal) {
            return balance;
        } else {
            return totalPay;
        }
    }

    @Mapping(target = "id", source = "saleId")
    @Mapping(target = "productName", expression = "java(venta.getProducto().getName())")
    @Mapping(target = "userName", source = "usuario.username")
    @Mapping(target = "date", expression = "java(formatDate(venta.getDate()))")
    public abstract VentaResponse mapToDto(Venta venta);

    String formatDate(Instant fecha) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return DATE_TIME_FORMATTER.format(fecha);
    }
}