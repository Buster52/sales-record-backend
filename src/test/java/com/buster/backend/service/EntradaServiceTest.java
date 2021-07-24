package com.buster.backend.service;

import com.buster.backend.dto.EntradaDto;
import com.buster.backend.mapper.EntradaMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Entrada;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.repository.EntradaRepository;
import com.buster.backend.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EntradaServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private EntradaRepository entradaRepository;
    @Mock
    private EntradaMapper entradaMapper;
    @Mock
    private AuthService authService;

    private EntradaService entradaService;

    @Captor
    ArgumentCaptor<Entrada> argumentCaptor;

    @BeforeEach
    void setUp() {
        entradaService = new EntradaService(productRepository, entradaRepository, entradaMapper, authService);
    }

    @Test
    void save() {
        Usuario currentUser = new Usuario(123L, "test user", "test",
                "secretpassword", "test@gmail.com", Instant.now(), true);

        Producto producto = new Producto(12L, "producto test", "description test",
                2L, 25.0, null, currentUser);

        Entrada entrada = new Entrada(1L, "first entry",
                producto, currentUser, 1, Instant.now());

        EntradaDto entradaDto = new EntradaDto(1L, "first entry", "producto test",
                1, "test user", null);

        Mockito.when(productRepository.findByName("producto test")).thenReturn(Optional.of(producto));
        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(entradaMapper.map(entradaDto, producto, currentUser)).thenReturn(entrada);

        entradaService.save(entradaDto);
        Mockito.verify(entradaRepository).save(argumentCaptor.capture());

        Assertions.assertThat(argumentCaptor.getValue().getId()).isEqualTo(1L);
        Assertions.assertThat(argumentCaptor.getValue().getUsuario().getUsername()).isEqualTo("test");
        assertEquals(3L, producto.getAmount());
    }
}