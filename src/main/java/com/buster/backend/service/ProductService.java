package com.buster.backend.service;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.mapper.ProductoMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.repository.CategoriaRepository;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductRepository productoRepo;
    private final AuthService authService;
    private final ProductoMapper productoMapper;

    @Transactional
    public ProductRequest save(ProductRequest productRequest) {
        Categoria categoria = categoriaRepository.findByName(productRequest.getCategory())
                .orElseThrow(() -> new CustomException(productRequest.getCategory()));
        Producto pr = productoRepo.save(productoMapper.map(productRequest, categoria, authService.getCurrentUser()));
        productRequest.setProductId(pr.getProductId());
        return productRequest;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new CustomException(id.toString()));
        return productoMapper.mapToDto(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productoRepo.findAll()
                .stream()
                .map(productoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductByCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CustomException(categoriaId.toString()));
        List<Producto> productos = productoRepo
                .findAllByCategoria(categoria);
        return productos.stream()
                .map(productoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(username));
        return productoRepo.findByUsuario(usuario)
                .stream()
                .map(productoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}