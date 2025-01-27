package com.dbserver.votacao.dto.VotingSession;

import com.dbserver.votacao.dto.Pauta.ListPautaDto;

import java.time.LocalDateTime;

public record ListVotingSessionDto(
        Long id,
        ListPautaDto pauta,
        LocalDateTime creationDate,
        Boolean active,
        LocalDateTime endDate
) {
}
