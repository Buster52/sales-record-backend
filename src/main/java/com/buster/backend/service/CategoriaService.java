package com.buster.backend.service;

import com.buster.backend.dto.CategoriaDTO;
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

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria save = categoriaRepository.save(categoriaMapper.fromDto(categoriaDTO));
        categoriaDTO.setId(save.getId());
        return categoriaDTO;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoriaDTO getCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("No category found with this id"));
        return categoriaMapper.toDto(categoria);
    }
}