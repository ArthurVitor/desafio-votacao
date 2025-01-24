package com.dbserver.votacao.dto.Pauta;

public record ListPautaDto(
        Long id,
        String title,
        String description,
        Boolean active,
        Integer lifeTime
) {
}
