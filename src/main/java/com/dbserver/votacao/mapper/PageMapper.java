package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Page.PageDto;
import org.springframework.data.domain.Page;

public class PageMapper {
    public static <T> PageDto<T> toDto(Page<T> page) {
        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
