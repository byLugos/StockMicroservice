package com.microservicio.stock.infraestructure;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.handler.BrandHandler;
import com.microservicio.stock.infraestructure.controllers.BrandController;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testListBrand_Success() {
        BrandDTO brandDTO = new BrandDTO(1L, "Books", "All kinds of books");
        Page<BrandDTO> page = new PageImpl<>(List.of(brandDTO));

        when(brandHandler.listBrands(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<BrandDTO>> response = brandController.listBrands(Pageable.unpaged());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1, response.getBody().getTotalElements());
    }
}