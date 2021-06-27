package com.buster.backend.mapper;

import com.buster.backend.dto.CategoriaDTO;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "numberOfProducts", expression = "java(mapProducts(categoria.getProducts()))")
    CategoriaDTO toDto(Categoria categoria);

    default Integer mapProducts(List<Producto> numberOfProducts) {
        return numberOfProducts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "products", ignore = true)
    Categoria fromDto(CategoriaDTO categoriaDTO);
}