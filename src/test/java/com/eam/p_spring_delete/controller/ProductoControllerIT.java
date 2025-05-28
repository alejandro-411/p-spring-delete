package com.eam.p_spring_delete.controller;

import com.eam.p_spring_delete.entity.Producto;
import com.eam.p_spring_delete.repository.ProductoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ProductoControllerIT {

    @Container
    static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.6.4")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDBContainer::getUsername);
        registry.add("spring.datasource.password", mariaDBContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void cleanDB() {
        productoRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        producto = new Producto(null, "P001", "Camisa", 50000.0, 10);
    }

    @Test
    void eliminarProducto_Existente_DeberiaRetornar200() throws Exception {
        mockMvc.perform(post("/api/productos/eliminar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/productos/eliminar/P001"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado exitosamente."));
    }

    @Test
    void eliminarProducto_NoExistente_DeberiaRetornar404() throws Exception {
        mockMvc.perform(delete("/api/productos/eliminar/NO_EXISTE"))
                .andExpect(status().isNotFound());
    }
}
