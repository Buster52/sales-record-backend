package com.buster.backend.controllers;

import com.buster.backend.dto.EntradaDto;
import com.buster.backend.security.JwtProvider;
import com.buster.backend.service.EntradaService;
import com.buster.backend.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EntradaController.class)
class EntradaControllerTest {

    @MockBean
    private EntradaService entradaService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Disabled
    @Test
    public void testGetAll() throws Exception {
        EntradaDto entradaDto = new EntradaDto(1L, "first entry", "test", 1, "buster", null);
        EntradaDto entradaDto1 = new EntradaDto(2L, "second entry", "test2", 1, "buster", null);

        Mockito.when(entradaService.getAll()).thenReturn(Arrays.asList(entradaDto, entradaDto1));

        mockMvc.perform(get("/api/entradas/"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)));
    }
}