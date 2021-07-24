package com.buster.backend.service;

import com.buster.backend.dto.ProductRequest;
import com.buster.backend.dto.ProductResponse;
import com.buster.backend.exceptions.AlreadyExistsException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.mapper.ProductoMapper;
import com.buster.backend.model.Categoria;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.repository.CategoriaRepository;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthService authService;

    @Mock
    private ProductoMapper productoMapper;

    @Captor
    private ArgumentCaptor<Producto> productoArgumentCaptor;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(usuarioRepository, categoriaRepository,
                productRepository, authService, productoMapper);
    }

    @Nested
    class testSaveMethod {
        Usuario currentUser = new Usuario(123L, "test user", "test",
                "secretpassword", "test@gmail.com", Instant.now(), true);
        Categoria categoria = new Categoria(123L, "Test categoria", currentUser);

        Producto producto = new Producto(12L, "producto test", "description test",
                2L, 25.0, null, null);

        ProductRequest productRequest = new ProductRequest(null, "producto test", "description test",
                "Test categoria", 2L, 25.0);

        @Test
        void itShouldThrownNotFoundException() {
            assertThrows(NotFoundException.class, () -> {
                productService.save(productRequest);
            });
        }

        @Test
        void itShouldThrownAlreadyExistException() {
            Mockito.when(categoriaRepository.findByName("Test categoria")).thenReturn(Optional.of(categoria));
            Mockito.when(productRepository.findByName("producto test")).thenReturn(Optional.of(producto));

            assertThrows(AlreadyExistsException.class, () -> {
                productService.save(productRequest);
            });
        }

        @Test
        void itShouldSave() {
            Mockito.when(categoriaRepository.findByName("Test categoria")).thenReturn(Optional.of(categoria));
            Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
            Mockito.when(productoMapper.map(productRequest, categoria, currentUser)).thenReturn(producto);

            productService.save(productRequest);
            Mockito.verify(productRepository).save(productoArgumentCaptor.capture());

            Assertions.assertThat(productoArgumentCaptor.getValue().getProductId()).isEqualTo(12L);
            Assertions.assertThat(productoArgumentCaptor.getValue().getName()).isEqualTo("producto test");
        }
    }

    @Nested
    class testGetProductById {
        Producto producto = new Producto(12L, "producto test", "description test",
                2L, 25.0, null, null);
        ProductResponse productResponse = new ProductResponse(12L, "producto test", "description test",
                25.0, 2L, "buster", "Test categoria");

        @Test
        void itShouldThrownNotFoundException() {
            assertThrows(NotFoundException.class, () -> {
                productService.getProductById(1L);
            });
        }

        @Test
        void itShouldReturnProduct() {
            Mockito.when(productRepository.findById(12L)).thenReturn(Optional.of(producto));
            Mockito.when(productoMapper.mapToDto(Mockito.any(Producto.class))).thenReturn(productResponse);

            ProductResponse productResponse1 = productService.getProductById(12L);

            Assertions.assertThat(productResponse.getId()).isEqualTo(productResponse1.getId());
            Assertions.assertThat(productResponse.getProductName()).isEqualTo(productResponse1.getProductName());
        }
    }

    @Test
    void getProductByCategoria() {
        Categoria categoria = new Categoria(123L, "Test categoria", null);

        List<Producto> products = new ArrayList<>();
        products.add(
                new Producto(12L, "producto test", "description test",
                        2L, 25.0, categoria, null)
        );
        products.add(new Producto(123L, "producto test1", "description test1",
                1L, 25.0, categoria, null));

        Mockito.when(categoriaRepository.findByName("Test categoria")).thenReturn(Optional.of(categoria));
        Mockito.when(productRepository.findAllByCategoria(Mockito.any(Categoria.class))).thenReturn(products);

        productService.getProductByCategoria("Test categoria");

        Assertions.assertThat(2).isEqualTo(products.size());
    }

    @Test
    void testGetProductByUsername() {
        Usuario usuario = new Usuario(123L, "test user", "test",
                "secretpassword", "test@gmail.com", Instant.now(), true);
        List<Producto> products = new ArrayList<>();
        products.add(
                new Producto(12L, "producto test", "description test",
                        2L, 25.0, null, usuario)
        );
        products.add(new Producto(123L, "producto test1", "description test1",
                1L, 25.0, null, usuario));

        Mockito.when(usuarioRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.when(productRepository.findByUsuario(Mockito.any(Usuario.class))).thenReturn(products);

        List<ProductResponse> response = products.stream().map(productoMapper::mapToDto).collect(Collectors.toList());
        productService.getProductsByUsername("test");

        Assertions.assertThat(2).isEqualTo(response.size());
    }
}