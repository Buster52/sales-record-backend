package com.buster.backend.service;

import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.mapper.CategoriaMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.repository.CategoriaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaServiceTest {

    private final CategoriaRepository categoriaRepository = Mockito.mock(CategoriaRepository.class);
    private final CategoriaMapper categoriaMapper = Mockito.mock(CategoriaMapper.class);
    private final AuthService authService = Mockito.mock(AuthService.class);

    @Test
    @DisplayName("Debe retornar todas las categorias")
    public void getAll() {
        CategoriaService categoriaService = new CategoriaService(categoriaRepository, categoriaMapper, authService);
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

}