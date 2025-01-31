package com.dbserver.votacao.dto.Associate;

import com.dbserver.votacao.dto.VotingSession.ListVotingSessionWithVoteDto;

import java.util.List;

public record ListAssociateVotesDto(
    ListAssociateDto associate,
    List<ListVotingSessionWithVoteDto> votingSessions
) {
}
