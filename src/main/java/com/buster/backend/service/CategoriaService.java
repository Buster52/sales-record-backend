package com.buster.backend.service;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.NotFoundException;
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
        if (categoriaRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new AlreadyExistsException("Ya existe categoria con nombre - " + categoryRequest.getName());
        }
        Categoria cat = categoriaRepository.save(categoriaMapper.map(categoryRequest, authService.getCurrentUser()));
        categoryRequest.setCategoryId(cat.getCategoryId());
        return categoryRequest;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() throws NotFoundException {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Categoria cat;
        if (categoriaRepository.findById(id).isEmpty()) {
            throw new NotFoundException("No existe categoria con id - " + id);
        } else {
            cat = categoriaRepository.findById(id).get();
        }
        return categoriaMapper.mapToDto(cat);
    }
}