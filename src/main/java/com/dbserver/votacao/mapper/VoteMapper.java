package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.Vote.CreateVoteDto;
import com.dbserver.votacao.dto.Vote.ListVoteDto;
import com.dbserver.votacao.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "associate", ignore = true)
    @Mapping(target = "votingSession", ignore = true)
    Vote toVote(CreateVoteDto vote);

    ListVoteDto toListVoteDto(Vote vote);

    @Mapping(target = "pageNumber", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    PageDto<ListVoteDto> toPageDto(Page<ListVoteDto> page);
}
