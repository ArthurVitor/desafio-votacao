package com.dbserver.votacao.repository;

import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVotingSessionAndAssociate(VotingSession votingSession, Associate associate);
}
