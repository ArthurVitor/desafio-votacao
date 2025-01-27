package com.dbserver.votacao.dto.VotingSession;

import java.time.LocalDateTime;

public record CreateVotingSessionDto(
    LocalDateTime endDate
) {
}
