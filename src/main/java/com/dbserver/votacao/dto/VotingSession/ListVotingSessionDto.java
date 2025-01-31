package com.dbserver.votacao.dto.VotingSession;

import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.enums.VotingSessionResultEnum;

import java.time.LocalDateTime;

public record ListVotingSessionDto(
        Long id,
        ListPautaDto pauta,
        LocalDateTime creationDate,
        LocalDateTime endDate,
        VotingSessionResultEnum result
) {
}
