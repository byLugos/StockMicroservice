package com.microservicio.stock.application.handler;
import com.microservicio.stock.application.dto.BrandDTO;
import com.microservicio.stock.application.mapper.BrandMapper;
import com.microservicio.stock.application.mapper.PageMapper;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.ports.api.BrandIn;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class BrandHandler {
    private final BrandIn brandIn;
    private final BrandMapper brandMapper;
    public BrandDTO createBrand(BrandDTO brandDTO) {

        Brand brand = brandMapper.toEntity(brandDTO);

        Brand newBrand = brandIn.createBrand(brand.getName(),brand.getDescription());

        return brandMapper.toDTO(newBrand);
    }
    public Page<BrandDTO> listBrands(Pageable pageable) {
        PageRequestCustom pageRequestCustom = new PageRequestCustom(pageable.getPageNumber(),pageable.getPageSize(),pageable.getSort().isSorted());
        PageCustom<Brand> pageCustom = brandIn.listBrand(pageRequestCustom);
        return PageMapper.toSpringPage(
                new PageCustom<>(
                        pageCustom.getContent().stream().map(brandMapper::toDTO).toList(),
                        pageCustom.getTotalElements(),
                        pageCustom.getTotalPages(),
                        pageCustom.getCurrentPage(),
                        pageCustom.isAscending()
                )
        );
    }
}
