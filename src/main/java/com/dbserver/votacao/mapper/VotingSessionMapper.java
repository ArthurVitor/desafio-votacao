package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.VotingSession.CreateVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionWithVoteDto;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {PautaMapper.class})
public interface VotingSessionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "result", ignore = true)
    VotingSession toEntity(CreateVotingSessionDto dto);

    ListVotingSessionDto toDto(VotingSession votingSession);

    @Mapping(target = "pageNumber", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    PageDto<ListVotingSessionDto> toPageDto(Page<ListVotingSessionDto> page);

    @Mapping(target = "id", source = "votingSession.id")
    @Mapping(target = "pauta", source = "votingSession.pauta")
    @Mapping(target = "creationDate", source = "votingSession.creationDate")
    @Mapping(target = "active", source = "votingSession.active")
    @Mapping(target = "endDate", source = "votingSession.endDate")
    @Mapping(target = "result", source = "votingSession.result")
    @Mapping(target = "voteOption", source = "voteOption")
    ListVotingSessionWithVoteDto toListVotingSessionWithVoteDto(Vote vote);
}
