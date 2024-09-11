package com.microservicio.stock.domain.pageable;

import java.util.List;
import java.util.function.Function;
public class PagingUtil {
    private PagingUtil() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad, no se puede instanciar");
    }
    public static <T> PageCustom<T> paginateAndSort(List<T> items, PageRequestCustom pageRequestCustom, Function<T, String> sortKeyExtractor) {
        // Ordenar los elementos
        List<T> sortedItems = items.stream()
                .sorted((item1, item2) -> {
                    int comparison = sortKeyExtractor.apply(item1).compareToIgnoreCase(sortKeyExtractor.apply(item2));
                    return pageRequestCustom.isAscending() ? comparison : -comparison;
                })
                .toList();
        // Calcular inicio y final para la sublista de la página actual
        int start = pageRequestCustom.getPage() * pageRequestCustom.getSize();
        int end = Math.min(start + pageRequestCustom.getSize(), sortedItems.size());
        // Crear la sublista para la página actual
        List<T> paginatedItems = sortedItems.subList(start, end);
        // Calcular el número total de páginas
        int totalElements = sortedItems.size();
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / pageRequestCustom.getSize());
        // Devolver el objeto PageCustom con los elementos paginados y la información adicional
        return new PageCustom<>(paginatedItems, totalElements, totalPages, pageRequestCustom.getPage(), pageRequestCustom.isAscending());
    }
}
