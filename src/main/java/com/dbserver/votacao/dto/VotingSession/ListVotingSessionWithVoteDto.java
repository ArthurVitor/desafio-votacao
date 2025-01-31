package com.dbserver.votacao.dto.VotingSession;

import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.enums.VoteOptionEnum;
import com.dbserver.votacao.enums.VotingSessionResultEnum;

import java.time.LocalDateTime;

public record ListVotingSessionWithVoteDto(
        Long id,
        ListPautaDto pauta,
        LocalDateTime creationDate,
        Boolean active,
        LocalDateTime endDate,
        VotingSessionResultEnum result,
        VoteOptionEnum voteOption
)  {
}
