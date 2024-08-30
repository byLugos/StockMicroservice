package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.mapper.BrandMapper;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.api.BrandIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class BrandHandlerTest {

    @Mock
    private BrandIn brandIn;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBrand_Success() {
        // Datos de ejemplo
        BrandDTO brandDTO = new BrandDTO(1L, "Nike", "Sportswear brand");
        Brand brand = new Brand(1L, "Nike", "Sportswear brand");

        when(brandMapper.toEntity(any(BrandDTO.class))).thenReturn(brand);
        when(brandIn.createBrand(anyString(), anyString())).thenReturn(brand);
        when(brandMapper.toDTO(any(Brand.class))).thenReturn(brandDTO);

        // Llamada al método y validación
        BrandDTO result = brandHandler.createBrand(brandDTO);
        assertEquals(brandDTO, result);
    }

    @Test
    void testListBrands_Success() {
        // Datos de ejemplo
        Brand brand1 = new Brand(1L, "Nike", "Sportswear brand");
        Brand brand2 = new Brand(1L, "Adidas", "Another sportswear brand");
        List<Brand> brands = List.of(brand1, brand2);

        PageCustom<Brand> pageCustom = new PageCustom<>(brands, 2, 1, 0, true);
        PageRequestCustom pageRequestCustom = new PageRequestCustom(0, 10, true);

        when(brandIn.listBrand(any(PageRequestCustom.class), anyString(), anyString())).thenReturn(pageCustom);

        BrandDTO brandDTO1 = new BrandDTO(1L, "Nike", "Sportswear brand");
        BrandDTO brandDTO2 = new BrandDTO(1L, "Adidas", "Another sportswear brand");

        when(brandMapper.toDTO(brand1)).thenReturn(brandDTO1);
        when(brandMapper.toDTO(brand2)).thenReturn(brandDTO2);

        // Llamada al método y validación
        Page<BrandDTO> resultPage = brandHandler.listBrands(pageRequestCustom, "Nike", "name");
        assertEquals(2, resultPage.getTotalElements());
        assertEquals(1, resultPage.getTotalPages());
        assertEquals(List.of(brandDTO1, brandDTO2), resultPage.getContent());
    }
}
