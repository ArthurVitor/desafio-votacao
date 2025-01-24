package com.dbserver.votacao.dto.Error;

public record ErrorResponseDto(
        String message,
        int statusCode
) {
}
