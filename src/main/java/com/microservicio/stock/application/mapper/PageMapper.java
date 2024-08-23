package com.microservicio.stock.application.mapper;
import com.microservicio.stock.domain.util.pageable.PageCustom;
import com.microservicio.stock.domain.util.pageable.PageRequestCustom;
import org.springframework.data.domain.*;


public class PageMapper {
    private PageMapper() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad, no se puede instanciar");
    }
    // Convertir PageRequestCustom a Pageable de Spring
    public static Pageable toSpringPageable(PageRequestCustom pageRequestCustom) {
        return PageRequest.of(pageRequestCustom.getPage(), pageRequestCustom.getSize(),
                pageRequestCustom.isAscending() ? org.springframework.data.domain.Sort.Direction.ASC : org.springframework.data.domain.Sort.Direction.DESC,
                "name"); // Asume que se está ordenando por "name"
    }

    // Convertir PageCustom a Page de Spring
    public static <T> Page<T> toSpringPage(PageCustom<T> pageCustom) {
        Pageable pageable = PageRequest.of(pageCustom.getCurrentPage(), pageCustom.getContent().size());
        return new PageImpl<>(pageCustom.getContent(), pageable, pageCustom.getTotalElements());
    }
}
