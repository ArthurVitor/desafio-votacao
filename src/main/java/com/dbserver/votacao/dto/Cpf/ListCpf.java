package com.dbserver.votacao.dto.Cpf;

import com.dbserver.votacao.enums.CpfAbleToVote;

public record ListCpf(
        CpfAbleToVote status
) {
}
