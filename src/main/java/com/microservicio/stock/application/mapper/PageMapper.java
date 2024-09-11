package com.microservicio.stock.application.mapper;
import com.microservicio.stock.domain.pageable.PageCustom;
import com.microservicio.stock.domain.pageable.PageRequestCustom;
import com.microservicio.stock.utils.Constants;
import org.springframework.data.domain.*;
public class PageMapper {
    private PageMapper() {
        throw new UnsupportedOperationException(Constants.UTILITY_CLASS);
    }
    public static Pageable toSpringPageable(PageRequestCustom pageRequestCustom) {
        return PageRequest.of(pageRequestCustom.getPage(), pageRequestCustom.getSize(),
                pageRequestCustom.isAscending() ? org.springframework.data.domain.Sort.Direction.ASC : org.springframework.data.domain.Sort.Direction.DESC,
                Constants.NAME);
    }
    public static <T> Page<T> toSpringPage(PageCustom<T> pageCustom) {
        Pageable pageable = PageRequest.of(pageCustom.getCurrentPage(), pageCustom.getContent().size());
        return new PageImpl<>(pageCustom.getContent(), pageable, pageCustom.getTotalElements());
    }
}
