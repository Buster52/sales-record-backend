package com.buster.backend.service;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.mapper.CategoriaMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final AuthService authService;

    @Transactional
    public CategoryRequest save(CategoryRequest categoryRequest) {
        Categoria save = categoriaRepository.save(categoriaMapper.map(categoryRequest, authService.getCurrentUser()));
        categoryRequest.setCategoryId(save.getCategoryId());
        return categoryRequest;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("No category found with this id"));
        return categoriaMapper.mapToDto(categoria);
    }
}