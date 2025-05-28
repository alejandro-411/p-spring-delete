package com.eam.p_spring_delete.service;

import com.eam.p_spring_delete.entity.Producto;
import com.eam.p_spring_delete.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Eliminar producto por código
    @Transactional
    public void eliminarProducto(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(
                        () -> new NoSuchElementException("No se encontró el producto con el código proporcionado"));

        productoRepository.delete(producto);
    }

}
