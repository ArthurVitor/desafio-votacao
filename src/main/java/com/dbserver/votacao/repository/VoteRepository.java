package com.dbserver.votacao.repository;

import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVotingSessionAndAssociate(VotingSession votingSession, Associate associate);

    List<Vote> findByAssociate(Associate associate);

    void deleteByVotingSessionAndAssociate(VotingSession votingSession, Associate associate);
}
