package com.buster.backend.service;

import com.buster.backend.dto.VentaRequest;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.exceptions.NotFoundException;
import com.buster.backend.mapper.VentaMapper;
import com.buster.backend.model.Producto;
import com.buster.backend.model.Usuario;
import com.buster.backend.model.Venta;
import com.buster.backend.repository.ProductRepository;
import com.buster.backend.repository.VentaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AuthService authService;
    @Mock
    private VentaMapper ventaMapper;

    @Captor
    ArgumentCaptor<Venta> ventaArgumentCaptor;

    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        ventaService = new VentaService(productRepository, ventaRepository, authService, ventaMapper);
    }

    @Nested
    class testSaveMethod {
        @Test
        void throwExceptionIfAmountIs0() {
            Producto producto = new Producto(123L, "test", "test description",
                    0L, 10.0, null, null);
            VentaRequest ventaRequest = new VentaRequest(12L, "buster", "test", 1L, 5.0);

            Mockito.when(productRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(producto));

            assertThrows(CustomException.class, () -> {
                ventaService.save(ventaRequest);
            });
        }

        @Test
        void saveSale() {
            Usuario currentUser = new Usuario(123L, "test user", "test",
                    "secretpassword", "test@gmail.com", Instant.now(), true);
            Producto producto1 = new Producto(1234L, "test",
                    "product description", 2L, 15.0, null, null);
            Venta venta = new Venta(1234L, "buster", producto1, 1L,
                    5.0, 10.0, 15.0, currentUser, Instant.now());
            VentaRequest ventaRequest = new VentaRequest(123L, "buster", "test", 1L, 5.0);

            Mockito.when(productRepository.findByName("test")).thenReturn(Optional.of(producto1));
            Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
            Mockito.when(ventaMapper.map(ventaRequest, producto1, currentUser)).thenReturn(venta);

            ventaService.save(ventaRequest);
            Mockito.verify(ventaRepository).save(ventaArgumentCaptor.capture());

            Assertions.assertThat(ventaArgumentCaptor.getValue().getSaleId()).isEqualTo(1234L);
            Assertions.assertThat(ventaArgumentCaptor.getValue().getBalance()).isEqualTo(10.0);
            assertEquals(1L, producto1.getAmount());
        }
    }

    @Nested
    class testUpdatePay {
        @Test
        void throwExceptionIfSaleDoesNotExist() {
            assertThrows(NotFoundException.class, () -> {
                ventaService.updatePay(12.0, 12L);
            });
        }

        @Test
        void updatePay() {
            Venta venta = new Venta(1234L, "buster", null, 1L,
                    5.0, 10.0, 15.0, null, Instant.now());

            Mockito.when(ventaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(venta));

            ventaService.updatePay(5.0, 1234L);

            Assertions.assertThat(5.0).isEqualTo(venta.getBalance());
        }
    }
}