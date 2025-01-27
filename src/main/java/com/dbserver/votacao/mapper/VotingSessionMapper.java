package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.VotingSession.CreateVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.model.VotingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface VotingSessionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    VotingSession toEntity(CreateVotingSessionDto dto);

    ListVotingSessionDto toDto(VotingSession votingSession);

    @Mapping(target = "pageNumber", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    PageDto<ListVotingSessionDto> toPageDto(Page<ListVotingSessionDto> page);
}
