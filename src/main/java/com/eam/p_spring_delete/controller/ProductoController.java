package com.eam.p_spring_delete.controller;

import com.eam.p_spring_delete.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String codigo) {
        try {
            productoService.eliminarProducto(codigo);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado exitosamente.");
            response.put("codigo", codigo);

            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
