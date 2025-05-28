package com.eam.p_spring_delete.service;

import com.eam.p_spring_delete.entity.Producto;
import com.eam.p_spring_delete.repository.ProductoRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoServiceIT {

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:10.6")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    private Producto producto;

    @BeforeEach
    void cleanDB() {
        productoRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        producto = new Producto(null, "P001", "Camisa", 50000.0, 10);
    }

    @Test
    @Order(1)
    void eliminarProducto_DeberiaEliminarCorrectamente() {
        // Crear producto directamente con el repositorio
        Producto productoInicial = new Producto(null, "E001", "Camisa", 50000.0, 10);
        productoRepository.save(productoInicial);

        // Ejecutar el método a probar
        productoService.eliminarProducto("E001");

        // Verificar directamente con el repositorio que el producto fue eliminado
        assertFalse(productoRepository.findByCodigo("E001").isPresent(),
                "El producto debería haber sido eliminado");
    }

    @Test
    @Order(2)
    void eliminarProducto_DeberiaFallarSiNoExiste() {
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            productoService.eliminarProducto("X999");
        });

        assertEquals("No se encontró el producto con el código proporcionado", ex.getMessage());
    }
}
