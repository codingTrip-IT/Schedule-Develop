package com.example.scheduledevelop.global.page;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * CustomResponsePage: 커스텀 응답 페이지
 * - 필드 : 내용, 페이지 번호, 페이지 크기, 총 페이지, 총 개수
 */
@Getter
public class CustomResponsePage<T> {

    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final int totalPages;
    private final long totalElements;

    public CustomResponsePage(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
