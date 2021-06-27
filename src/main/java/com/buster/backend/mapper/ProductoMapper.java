package com.buster.backend.mapper;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(target = "categoria", source = "categoria")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "name", source = "productRequest.productName")
    @Mapping(target = "description", source = "productRequest.description")
    Producto map(ProductRequest productRequest, Categoria categoria, Usuario usuario);

    @Mapping(target = "id", source = "productId")
    @Mapping(target = "productName", source = "name")
    @Mapping(target = "category", source = "categoria.name")
    @Mapping(target = "userName", source = "usuario.username")
    ProductResponse mapToDto(Producto producto);
}
