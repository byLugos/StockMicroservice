package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.mapper.BrandMapper;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.api.BrandIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandHandlerTest {

    private BrandIn brandIn;
    private BrandMapper brandMapper;
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        brandIn = mock(BrandIn.class);
        brandMapper = mock(BrandMapper.class);
        brandHandler = new BrandHandler(brandIn, brandMapper);
    }

    @Test
    void testCreateBrand_Success() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("New Brand");
        brandDTO.setDescription("Description");

        Brand brandEntity = new Brand(null, "New Brand", "Description");
        Brand savedBrand = new Brand(1L, "New Brand", "Description");

        when(brandMapper.toEntity(brandDTO)).thenReturn(brandEntity);
        when(brandIn.createBrand(anyString(), anyString())).thenReturn(savedBrand);
        when(brandMapper.toDTO(savedBrand)).thenReturn(brandDTO);

        BrandDTO result = brandHandler.createBrand(brandDTO);

        verify(brandMapper).toEntity(brandDTO);
        verify(brandIn).createBrand(brandEntity.getName(), brandEntity.getDescription());
        verify(brandMapper).toDTO(savedBrand);

        assertNotNull(result);
        assertEquals(brandDTO.getName(), result.getName());
        assertEquals(brandDTO.getDescription(), result.getDescription());
    }

    @Test
    void testListBrands_Success() {
        Brand brand1 = new Brand(1L, "Brand1", "Description1");
        Brand brand2 = new Brand(2L, "Brand2", "Description2");
        List<Brand> brands = Arrays.asList(brand1, brand2);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Brand1");
        brandDTO1.setDescription("Description1");

        BrandDTO brandDTO2 = new BrandDTO();
        brandDTO2.setId(2L);
        brandDTO2.setName("Brand2");
        brandDTO2.setDescription("Description2");
        PageCustom<Brand> pageCustom = new PageCustom<>(brands, brands.size(), 1, 0, true);

        when(brandIn.listBrand(any(PageRequestCustom.class))).thenReturn(pageCustom);
        when(brandMapper.toDTO(brand1)).thenReturn(brandDTO1);
        when(brandMapper.toDTO(brand2)).thenReturn(brandDTO2);


        Pageable pageable = PageRequest.of(0, 2);
        Page<BrandDTO> result = brandHandler.listBrands(pageable);

        ArgumentCaptor<PageRequestCustom> pageRequestCaptor = ArgumentCaptor.forClass(PageRequestCustom.class);
        verify(brandIn).listBrand(pageRequestCaptor.capture());
        assertEquals(0, pageRequestCaptor.getValue().getPage());
        assertEquals(2, pageRequestCaptor.getValue().getSize());

        verify(brandMapper).toDTO(brand1);
        verify(brandMapper).toDTO(brand2);


        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(brandDTO1.getName(), result.getContent().get(0).getName());
        assertEquals(brandDTO2.getName(), result.getContent().get(1).getName());
    }
}
