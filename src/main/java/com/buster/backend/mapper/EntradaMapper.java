package com.buster.backend.mapper;

import com.buster.backend.dto.EntradaDto;
import com.buster.backend.model.Entrada;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EntradaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "entradaDto.description")
    @Mapping(target = "amount", source = "entradaDto.amount")
    @Mapping(target = "producto", source = "producto")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    Entrada map(EntradaDto entradaDto, Producto producto, Usuario usuario);

    @Mapping(target = "productName", expression = "java(entrada.getProducto().getName())")
    @Mapping(target = "userName", expression = "java(entrada.getUsuario().getUsername())")
    @Mapping(target = "date", expression = "java(formatDate(entrada.getDate()))")
    EntradaDto mapToDto(Entrada entrada);

    default String formatDate(Instant fecha) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return DATE_TIME_FORMATTER.format(fecha);
    }
}