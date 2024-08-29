package com.microservicio.stock.domain.service;

import com.microservicio.stock.domain.exception.InvalidNameExceptionMe;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.spi.BrandOut;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandServiceTest {

    private BrandOut brandOut;
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        brandOut = mock(BrandOut.class);
        brandService = new BrandService(brandOut);
    }

    @Test
    void testCreateBrand_Success() {
        when(brandOut.existsByName("New Brand")).thenReturn(false);
        Brand brandToSave = new Brand(null, "New Brand", "New Description");
        when(brandOut.save(any(Brand.class))).thenReturn(brandToSave);

        Brand result = brandService.createBrand("New Brand", "New Description");

        verify(brandOut).existsByName("New Brand");
        verify(brandOut).save(any(Brand.class));


        assertNotNull(result);
        assertEquals("New Brand", result.getName());
        assertEquals("New Description", result.getDescription());
    }

    @Test
    void testCreateBrand_InvalidNameException() {
        when(brandOut.existsByName("Existing Brand")).thenReturn(true);

        InvalidNameExceptionMe exception = assertThrows(InvalidNameExceptionMe.class, () -> {
            brandService.createBrand("Existing Brand", "Description");
        });

        assertEquals("El nombre de la marca ya existe", exception.getMessage());
        verify(brandOut).existsByName("Existing Brand");
        verify(brandOut, never()).save(any(Brand.class));
    }

    @Test
    void testListBrand_Success() {
        List<Brand> brands = Arrays.asList(
                new Brand(1L, "Brand1", "Description1"),
                new Brand(2L, "Brand2", "Description2")
        );
        when(brandOut.findAll()).thenReturn(brands);

        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 2, true);

        PageCustom<Brand> result = brandService.listBrand(pageRequestCustom);

        verify(brandOut).findAll();

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Brand1", result.getContent().get(0).getName());
        assertEquals("Brand2", result.getContent().get(1).getName());
    }
}
