package com.microservicio.stock.domain.util.pageable;


import java.util.List;

public class PageCustom<T>{
    private List<T> content;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean ascending;
    private boolean empty;
    public PageCustom(List<T> content, int totalElements, int totalPages, int currentPage, boolean ascending) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.ascending = ascending;
        this.empty = content.isEmpty(); // Calcula si la lista está vacía
    }
    public List<T> getContent() {
        return content;
    }
    public int getTotalElements() {
        return totalElements;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public boolean isAscending() {
        return ascending;
    }
    public boolean isEmpty() {
        return empty;
    }
}
