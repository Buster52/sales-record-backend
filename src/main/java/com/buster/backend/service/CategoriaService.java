package com.buster.backend.service;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.mapper.CategoriaMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private  CategoriaMapper categoriaMapper;
    @Autowired
    private  AuthService authService;

    @Transactional
    public void save(CategoryRequest categoryRequest) {
        if (categoriaRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new AlreadyExistsException("Ya existe categoria con nombre - " + categoryRequest.getName());
        }
        Categoria catMapped = categoriaMapper.map(categoryRequest, authService.getCurrentUser());
        if (catMapped == null) {
            throw new CustomException("El objeto mapeado es nulo");
        }
        categoriaRepository.save(catMapped);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() throws NotFoundException {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe categoria con ese id"));
        return categoriaMapper.mapToDto(cat);
    }
}
