package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandServiceTest {

    @Mock
    private BrandOut brandOut;

    @InjectMocks
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBrand_Sucesful() {
        String nombre = "Electronics";
        String descripcion = "All electronic items";

        when(brandOut.existsByName(nombre)).thenReturn(false);
        when(brandOut.save(any(Brand.class))).thenReturn(new Brand(1L, nombre, descripcion));

        Brand result = brandService.createBrand(nombre, descripcion);

        assertNotNull(result);
        assertEquals(nombre, result.getName());
        assertEquals(descripcion, result.getDescription());
    }

    @Test
    void testCreateBrand_NameAlreadyExists() {
        String nombre = "Electronics";
        String descripcion = "All electronic items";

        when(brandOut.existsByName(nombre)).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> {
            brandService.createBrand(nombre, descripcion);
        });

        assertEquals("El nombre de la marca ya existe", exception.getMessage());
    }
}