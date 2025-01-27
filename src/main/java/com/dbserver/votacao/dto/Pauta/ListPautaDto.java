package com.dbserver.votacao.dto.Pauta;

import java.time.LocalDateTime;

public record ListPautaDto(
        Long id,
        String title,
        String description,
        LocalDateTime creationDate
) {
}
