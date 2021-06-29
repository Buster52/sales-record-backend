package com.buster.backend.mapper;

import com.buster.backend.dto.CategoryRequest;
import com.buster.backend.dto.CategoryResponse;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "name", source = "categoryRequest.name")
    @Mapping(target = "products", ignore = true)
    Categoria map(CategoryRequest categoryRequest, Usuario usuario);

    @Mapping(target = "id", source = "categoryId")
    @Mapping(target = "categoryName", source = "name")
    @Mapping(target = "numberOfProducts", expression = "java(mapProducts(categoria.getProducts()))")
    @Mapping(target = "userName", source = "usuario.username")
    CategoryResponse mapToDto(Categoria categoria);

    default Integer mapProducts(List<Producto> numberOfProducts) {
        return numberOfProducts.size();
    }
}