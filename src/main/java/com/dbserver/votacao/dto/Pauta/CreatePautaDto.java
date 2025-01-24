package com.dbserver.votacao.dto.Pauta;

public record CreatePautaDto(
        String title,
        String description,
        Boolean active,
        Integer lifeTime
) {
}
