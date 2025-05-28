package com.eam.p_spring_delete.controller;

import com.eam.p_spring_delete.service.ProductoService;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductoService productoService;


        @Test
        void eliminarProducto_DeberiaRetornarOk() throws Exception {
                doNothing().when(productoService).eliminarProducto("P001");

                mockMvc.perform(delete("/api/productos/eliminar/P001"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Producto eliminado exitosamente."));
        }

        @Test
        void eliminarProducto_DeberiaRetornarNotFoundSiNoExiste() throws Exception {
                doThrow(new NoSuchElementException("No se encontró el producto")).when(productoService)
                                .eliminarProducto("P001");

                mockMvc.perform(delete("/api/productos/eliminar/P001"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("No se encontró el producto"));
        }
}
