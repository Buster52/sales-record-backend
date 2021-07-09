package com.buster.backend.controllers;

import com.buster.backend.model.Entrada;
import com.buster.backend.repository.EntradaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntradaControllerTest {

    @Autowired
    EntradaRepository entradaRepository;

    @Test
    public void testCreate() {
    }

}