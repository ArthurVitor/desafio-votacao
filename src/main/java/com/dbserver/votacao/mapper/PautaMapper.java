package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.model.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PautaMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "creationDate", target = "creationDate")
    ListPautaDto toDto(Pauta pauta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    Pauta toPauta(CreatePautaDto dto);

    @Mapping(target = "pageNumber", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    PageDto<ListPautaDto> toPageDto(Page<ListPautaDto> page);
}
