package com.eam.p_spring_delete.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.NoSuchElementException;

import com.eam.p_spring_delete.repository.ProductoRepository;
import com.eam.p_spring_delete.entity.Producto;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void eliminarProductoPorCodigo_DeberiaEliminarProductoSiExiste() {
        Producto producto = new Producto(1L, "P001", "Camisa", 50000.0, 10);
        when(productoRepository.findByCodigo("P001")).thenReturn(Optional.of(producto));

        productoService.eliminarProducto("P001");

        verify(productoRepository).delete(producto);
    }

    @Test
    void eliminarProductoPorCodigo_DeberiaLanzarExcepcionSiNoExiste() {
        when(productoRepository.findByCodigo("P999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            productoService.eliminarProducto("P999");
        });

        assertEquals("No se encontró el producto con el código proporcionado", exception.getMessage());
        verify(productoRepository, never()).delete(any());
    }

}
