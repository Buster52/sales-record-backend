package com.buster.backend.mapper;

import com.buster.backend.dto.VentaDto;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.model.Venta;
import com.buster.backend.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class VentaMapper {

    @Autowired
    private ProductRepository productoRepo;

    @Mapping(target = "saleId", ignore = true)
    @Mapping(target = "clientName", source = "ventaDto.clientName")
    @Mapping(target = "amount", source = "ventaDto.amount")
    @Mapping(target = "pay", source = "ventaDto.pay")
    @Mapping(target = "balance", expression = "java(getTotal(producto.getProductId(), ventaDto, false))")
    @Mapping(target = "total", expression = "java(getTotal(producto.getProductId(), ventaDto, true))")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "producto", source = "producto")
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    public abstract Venta map(VentaDto ventaDto, Producto producto, Usuario usuario);

    Double getTotal(Long id, VentaDto ventaDto, boolean isTotal) {
        Producto producto = productoRepo.findBydIdProduct(id);
        Double totalPay = ventaDto.getAmount() * producto.getPrice();
        Double balance = totalPay - ventaDto.getPay();

        if (!isTotal) {
            return balance;
        } else {
            return totalPay;
        }
    }
}