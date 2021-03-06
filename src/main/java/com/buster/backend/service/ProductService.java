package com.buster.backend.service;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.mapper.ProductoMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.repository.CategoriaRepository;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.repository.UsuarioRepository;
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
public class ProductService {
  @Autowired
    private  UsuarioRepository usuarioRepository;
  @Autowired
    private  CategoriaRepository categoriaRepository;
  @Autowired
    private  ProductRepository productoRepo;
  @Autowired
    private  AuthService authService;
  @Autowired
    private  ProductoMapper productoMapper;

    @Transactional
    public void save(ProductRequest productRequest) {
        Categoria categoria = categoriaRepository.findByName(productRequest.getCategory())
                .orElseThrow(() -> new NotFoundException("No existe categoria con ese nombre"));
        if (productoRepo.findByName(productRequest.getProductName()).isPresent()) {
            throw new AlreadyExistsException("Ya existe producto con ese nombre");
        }
        Producto productMapped = productoMapper.map(productRequest, categoria, authService.getCurrentUser());
        productoRepo.save(productMapped);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe producto con id - " + id));
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
    public List<ProductResponse> getProductByCategoria(String categoryName) {
        Categoria categoria = categoriaRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException("No existe esta categoria."));
        List<Producto> productos = productoRepo
                .findAllByCategoria(categoria);
        return productos.stream()
                .map(productoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("No existe este usuario."));
        return productoRepo.findByUsuario(usuario)
                .stream()
                .map(productoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
