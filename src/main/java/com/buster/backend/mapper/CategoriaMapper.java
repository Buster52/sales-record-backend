package com.buster.backend.mapper;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Usuario;
import com.buster.backend.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CategoriaMapper {
    @Autowired
    private ProductRepository productRepository;

    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "name", source = "categoryRequest.name")
    public abstract Categoria map(CategoryRequest categoryRequest, Usuario usuario);

    @Mapping(target = "id", source = "categoryId")
    @Mapping(target = "categoryName", source = "name")
    @Mapping(target = "numberOfProducts", expression = "java(mapProducts(categoria))")
    @Mapping(target = "userName", source = "usuario.username")
    public abstract CategoryResponse mapToDto(Categoria categoria);

    Integer mapProducts(Categoria categoria) {
        return productRepository.findAllByCategoria(categoria).size();
    }
}