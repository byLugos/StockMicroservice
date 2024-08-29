package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandControllerTest {

    private BrandHandler brandHandler;
    private BrandController brandController;

    @BeforeEach
    void setUp() {
        brandHandler = mock(BrandHandler.class);
        brandController = new BrandController(brandHandler);
    }

    @Test
    void testCreateCategory_Success() {
        // Configura el DTO de entrada y el resultado esperado
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("New Brand");
        brandDTO.setDescription("Description");

        BrandDTO savedBrandDTO = new BrandDTO();
        savedBrandDTO.setId(1L);
        savedBrandDTO.setName("New Brand");
        savedBrandDTO.setDescription("Description");

        // Configura el comportamiento del handler
        when(brandHandler.createBrand(brandDTO)).thenReturn(savedBrandDTO);

        // Llama al método a probar
        ResponseEntity<BrandDTO> response = brandController.createCategory(brandDTO);

        // Verifica que el handler fue llamado correctamente
        verify(brandHandler).createBrand(brandDTO);

        // Verifica los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedBrandDTO, response.getBody());
    }

    @Test
    void testListBrands_Success() {
        // Configura una lista simulada de marcas
        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Brand1");
        brandDTO1.setDescription("Description1");

        BrandDTO brandDTO2 = new BrandDTO();
        brandDTO2.setId(2L);
        brandDTO2.setName("Brand2");
        brandDTO2.setDescription("Description2");

        List<BrandDTO> brands = Arrays.asList(brandDTO1, brandDTO2);
        Page<BrandDTO> brandPage = new PageImpl<>(brands);

        // Configura el comportamiento del handler
        Pageable pageable = PageRequest.of(0, 2);
        when(brandHandler.listBrands(pageable)).thenReturn(brandPage);

        // Llama al método a probar
        ResponseEntity<Page<BrandDTO>> response = brandController.listBrands(pageable);

        // Verifica que el handler fue llamado correctamente
        verify(brandHandler).listBrands(pageable);

        // Verifica los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandPage, response.getBody());
    }
}
