package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Associate.CreateAssociateDto;
import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.model.Associate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AssociateMapper {
    @Mapping(target = "id", ignore = true)
    Associate toAssociate(CreateAssociateDto dto);

    ListAssociateDto toDto(Associate associate);

    @Mapping(target = "pageNumber", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    PageDto<ListAssociateDto> toPageDto(Page<ListAssociateDto> page);
}
