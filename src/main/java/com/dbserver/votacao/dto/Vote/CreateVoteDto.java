package com.dbserver.votacao.dto.Vote;

import com.dbserver.votacao.enums.VoteOptionEnum;

public record CreateVoteDto(
        VoteOptionEnum voteOption
) {
}
