package com.microservicio.stock.aplication.handler;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
import com.microservicio.stock.application.mapper.BrandMapper;
import com.microservicio.stock.application.mapper.PageMapper;
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
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testListBrand_Success() {
        Brand brand = new Brand(1L, "Books", "All kinds of books");
        BrandDTO brandDTO = new BrandDTO(1L, "Books", "All kinds of books");

        when(brandIn.listBrand(any(PageRequestCustom.class))).thenReturn(new PageCustom<>(List.of(brand), 1, 1, 0, true));
        when(brandMapper.toDTO(any(Brand.class))).thenReturn(brandDTO);

        Pageable pageable = PageMapper.toSpringPageable(new PageRequestCustom(0, 10, true));
        Page<BrandDTO> result = brandHandler.listBrands(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(brandDTO, result.getContent().get(0));
    }
}