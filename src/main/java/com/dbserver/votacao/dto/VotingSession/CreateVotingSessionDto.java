package com.dbserver.votacao.dto.VotingSession;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateVotingSessionDto(
    @NotNull()
    LocalDateTime endDate
) {
}
