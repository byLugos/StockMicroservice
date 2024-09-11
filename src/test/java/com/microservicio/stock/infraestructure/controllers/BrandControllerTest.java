package com.microservicio.stock.infraestructure.controllers;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class BrandControllerTest {

    @Mock
    private BrandHandler brandHandler;

    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBrand_Success() {
        // Datos de ejemplo
        BrandDTO brandDTO = new BrandDTO(1L, "Nike", "Sportswear brand");
        when(brandHandler.createBrand(any(BrandDTO.class))).thenReturn(brandDTO);

        // Llamada al método y validación
        ResponseEntity<BrandDTO> response = brandController.createCategory(brandDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(brandDTO, response.getBody());
    }

    @Test
    void testListBrands_Success() {
        // Datos de ejemplo
        BrandDTO brand1 = new BrandDTO(1L, "Nike", "Sportswear brand");
        BrandDTO brand2 = new BrandDTO(1L, "Adidas", "Another sportswear brand");
        List<BrandDTO> brandList = List.of(brand1, brand2);
        Page<BrandDTO> mockPage = new PageImpl<>(brandList);

        // Mock del comportamiento
        when(brandHandler.listBrands(any(PageRequestCustom.class), anyString(), anyString())).thenReturn(mockPage);

        // Parámetros de prueba
        String name = "Nike";
        String sort = "name";
        String direction = "asc";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Llamada al método y validación
        ResponseEntity<Page<BrandDTO>> response = brandController.listCategories(name, sort, direction, pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }

    @Test
    void testListBrands_InvalidParameters() {
        // Datos de ejemplo de respuesta vacía
        Page<BrandDTO> emptyPage = Page.empty();
        when(brandHandler.listBrands(any(PageRequestCustom.class), anyString(), anyString())).thenReturn(emptyPage);

        // Parámetros de prueba con nombre no existente
        String name = "NonExistent";
        String sort = "name";
        String direction = "asc";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Llamada al método y validación
        ResponseEntity<Page<BrandDTO>> response = brandController.listCategories(name, sort, direction, pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).getTotalElements());
    }
}
