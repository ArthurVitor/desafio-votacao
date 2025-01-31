package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Associate.ListAssociateVotesDto;
import com.dbserver.votacao.dto.Vote.CreateVoteDto;
import com.dbserver.votacao.dto.Vote.ListVoteDto;
import com.dbserver.votacao.exception.Conflict.AssociateAlreadyVotedException;
import com.dbserver.votacao.exception.NotFound.AssociateNotFoundException;
import com.dbserver.votacao.exception.BadRequest.SessionAlreadyFinishedException;
import com.dbserver.votacao.exception.NotFound.VotingSessionNotFoundException;
import com.dbserver.votacao.mapper.AssociateMapper;
import com.dbserver.votacao.mapper.VoteMapper;
import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import com.dbserver.votacao.repository.AssociateRepository;
import com.dbserver.votacao.repository.VoteRepository;
import com.dbserver.votacao.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service()
@RequiredArgsConstructor()
public class VoteService {
    private final VoteRepository voteRepository;
    private final AssociateRepository associateRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final VoteMapper voteMapper;
    private final AssociateMapper associateMapper;

    public ListVoteDto vote(CreateVoteDto vote, Long associateId, Long votingSessionId) {
        VotingSession votingSession = this.getVotingSession(votingSessionId);
        Associate associate = this.getAssociate(associateId);

        if(this.isVotingSessionFinished(votingSession)) {
            votingSession.setActive(false);
            this.votingSessionRepository.save(votingSession);

            throw new SessionAlreadyFinishedException("Voting session already finished. Check results");
        }

        if(this.hasAssociateAlreadyVoted(associate, votingSession)) {
            throw new AssociateAlreadyVotedException("Associate already voted on this voting session");
        }

        Vote voteEntity = voteMapper.toVote(vote);
        voteEntity.setAssociate(associate);
        voteEntity.setVotingSession(votingSession);

        return voteMapper.toListVoteDto(voteRepository.save(voteEntity));
    }

    @Transactional
    public void remove(Long associateId, Long votingSessionId) {
        Associate associate = this.getAssociate(associateId);
        VotingSession votingSession = this.getVotingSession(votingSessionId);

        if (this.isVotingSessionFinished(votingSession)) {
            throw new SessionAlreadyFinishedException("Voting session already finished. Check results");
        }

        this.voteRepository.deleteByVotingSessionAndAssociate(votingSession, associate);
    }

    public ListAssociateVotesDto getAssociateVotes(Long associateId) {
         Associate associate = this.getAssociate(associateId);
         List<Vote> votes = this.voteRepository.findByAssociate(associate);

         return this.associateMapper.toAssociateVotes(associate, votes);
    }

    private VotingSession getVotingSession(Long id) {
        return this.votingSessionRepository.findById(id).orElseThrow(() -> new VotingSessionNotFoundException("Voting Session Not Found with id " + id));
    }

    private Associate getAssociate(Long id) {
        return this.associateRepository.findById(id).orElseThrow(() -> new AssociateNotFoundException("Could not find associate with id " + id));
    }

    private boolean isVotingSessionFinished(VotingSession votingSession) {
        return votingSession.getEndDate().isBefore(LocalDateTime.now());
    }

    private boolean hasAssociateAlreadyVoted(Associate associate, VotingSession votingSession) {
        return this.voteRepository.existsByVotingSessionAndAssociate(votingSession, associate);
    }
}
