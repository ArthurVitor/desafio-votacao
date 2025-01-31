package com.dbserver.votacao.dto.VotingSession;

import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.enums.VoteOptionEnum;

import java.time.LocalDateTime;

public record ListVotingSessionWithVoteDto(
        Long id,
        ListPautaDto pauta,
        LocalDateTime creationDate,
        Boolean active,
        LocalDateTime endDate,
        VoteOptionEnum voteOption
)  {
}
