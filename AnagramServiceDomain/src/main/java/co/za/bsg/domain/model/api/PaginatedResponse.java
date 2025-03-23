package co.za.bsg.domain.model.api;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

    public List<T> getContent() {
        return content;
    }

    public PaginatedResponse<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public PaginatedResponse<T> setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PaginatedResponse<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public PaginatedResponse<T> setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public PaginatedResponse<T> setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    @Override
    public String toString() {
        return "PaginatedResponse{" +
                "content=" + content +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
