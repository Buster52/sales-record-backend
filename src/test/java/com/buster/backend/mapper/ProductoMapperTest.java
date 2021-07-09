package com.buster.backend.mapper;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProductoMapperTest {

    private final ProductoMapper productoMapper = new ProductoMapperImpl();

    Usuario user = new Usuario(
            2L, "Edgardo Gonzalez", "buster52", "123456",
            "e.gonzalez@mail.com", Instant.now(), true);

    Categoria cat = new Categoria(2L, "cat test", user);

    @Test
    void testMap() {
        ProductRequest productRequest = ProductRequest.builder()
                .productId(1L)
                .productName("Teclado HyperX")
                .description("Teclado gaming")
                .category(cat.getName())
                .amount(3L)
                .price(35.0)
                .build();

        Producto producto = productoMapper.map(productRequest, cat, user);

        assertAll(
                () -> {
                    assertEquals(producto.getName(), productRequest.getProductName());
                    assertEquals(producto.getDescription(), productRequest.getDescription());
                    assertEquals(producto.getCategoria().getName(), productRequest.getCategory());
                    assertEquals(producto.getAmount(), productRequest.getAmount());
                    assertEquals(producto.getPrice(), productRequest.getPrice());
                }
        );
    }

    @Test
    void testMapToDto() {
        Producto producto = Producto.builder()
                .productId(2L)
                .name("first")
                .categoria(cat)
                .amount(2L)
                .description("first product")
                .price(50.0)
                .usuario(user)
                .build();


        ProductResponse productResponse = productoMapper.mapToDto(producto);

        assertAll(
                () -> {
                    assertEquals(producto.getName(), productResponse.getProductName());
                    assertEquals(producto.getDescription(), productResponse.getDescription());
                    assertEquals(producto.getCategoria().getName(), productResponse.getCategory());
                    assertEquals(producto.getAmount(), productResponse.getAmount());
                    assertEquals(producto.getPrice(), productResponse.getPrice());
                    assertEquals(producto.getUsuario().getUsername(), productResponse.getUserName());
                }
        );
    }
}