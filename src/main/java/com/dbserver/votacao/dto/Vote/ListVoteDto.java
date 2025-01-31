package com.dbserver.votacao.dto.Vote;

import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.enums.VoteOptionEnum;

public record ListVoteDto(
        Long id,
        ListAssociateDto associate,
        ListVotingSessionDto votingSession,
        VoteOptionEnum voteOption
) {
}
