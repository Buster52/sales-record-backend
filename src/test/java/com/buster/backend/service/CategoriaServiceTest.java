package com.buster.backend.service;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.mapper.CategoriaMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Usuario;
import com.buster.backend.repository.CategoriaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @Mock
    private AuthService authService;

    @Captor
    private ArgumentCaptor<Categoria> categoriaArgumentCaptor;

    private CategoriaService categoriaService;

    @BeforeEach
    public void setup() {
        categoriaService = new CategoriaService(categoriaRepository, categoriaMapper, authService);
    }

    @Test
    @DisplayName("Debe retornar categoia por id")
    public void testGetById() {
        Categoria c1 = new Categoria(1L, "Gaming", null);
        CategoryResponse expected = new CategoryResponse(
                1L,
                "Gaming",
                2,
                "buster"
        );

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(c1));
        Mockito.when(categoriaMapper.mapToDto(Mockito.any(Categoria.class))).thenReturn(expected);

        CategoryResponse categoryResponse = categoriaService.getCategoryById(1L);

        Assertions.assertThat(categoryResponse.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(categoryResponse.getCategoryName()).isEqualTo(expected.getCategoryName());
    }

    @Test
    @Disabled
    @DisplayName("Guardar categoria")
    public void testSave() {
        Usuario currentUser = new Usuario(123L, "test user", "test",
                "secretpassword", "test@gmail.com", Instant.now(), true);
        Categoria categoria = new Categoria(123L, "First category", currentUser);
        CategoryRequest categoryRequest = new CategoryRequest(null, "First category");

        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(categoriaMapper.map(categoryRequest, currentUser)).thenReturn(categoria);

        categoriaService.save(categoryRequest);
        Mockito.verify(categoriaRepository).save(categoriaArgumentCaptor.capture());

        Assertions.assertThat(categoriaArgumentCaptor.getValue().getCategoryId()).isEqualTo(123L);
    }
}