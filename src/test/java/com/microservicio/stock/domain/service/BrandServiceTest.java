package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;
import java.util.List;

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
    void testCreateBrand_Success() {
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
    @Test
    void testListBrand_Success() {
        List<Brand> brands = List.of(new Brand(1L, "Books", "All kinds of books"), new Brand(2L, "Electronics", "All electronic items"));
        when(brandOut.findAll()).thenReturn(brands);

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);
        PageCustom<Brand> result = brandService.listBrand(pageRequestCustom);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void testListBrand_Empty() {
        when(brandOut.findAll()).thenReturn(Collections.emptyList());

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);
        PageCustom<Brand> result = brandService.listBrand(pageRequestCustom);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
    }
}