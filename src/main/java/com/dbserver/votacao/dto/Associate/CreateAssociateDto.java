package com.dbserver.votacao.dto.Associate;

import jakarta.validation.constraints.NotNull;

public record CreateAssociateDto(
        @NotNull()
        String name,
        @NotNull()
        String cpf,
        @NotNull()
        String email
) {
}
