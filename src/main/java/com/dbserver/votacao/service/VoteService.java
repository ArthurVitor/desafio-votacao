package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Vote.CreateVoteDto;
import com.dbserver.votacao.dto.Vote.ListVoteDto;
import com.dbserver.votacao.exception.AssociateNotFoundException;
import com.dbserver.votacao.exception.BadRequestException;
import com.dbserver.votacao.exception.VotingSessionNotFoundException;
import com.dbserver.votacao.mapper.VoteMapper;
import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import com.dbserver.votacao.repository.AssociateRepository;
import com.dbserver.votacao.repository.VoteRepository;
import com.dbserver.votacao.repository.VotingSessionRepository;
import org.springframework.stereotype.Service;

@Service()
public class VoteService {
    private final VoteRepository voteRepository;
    private final AssociateRepository associateRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final VoteMapper voteMapper;

    public VoteService(VoteRepository voteRepository, AssociateRepository associateRepository, VotingSessionRepository votingSessionRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.associateRepository = associateRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.voteMapper = voteMapper;
    }

    public ListVoteDto createVote(CreateVoteDto vote, Long associateId, Long votingSessionId) {
        VotingSession votingSession = votingSessionRepository.findById(votingSessionId).orElseThrow(() -> new VotingSessionNotFoundException("Voting Session Not Found with id " + votingSessionId));
        Associate associate = associateRepository.findById(associateId).orElseThrow(() -> new AssociateNotFoundException("Could not find associate with id " + associateId));

        boolean hasVoted = this.voteRepository.existsByVotingSessionAndAssociate(votingSession, associate);
        if (hasVoted) {
            throw new BadRequestException("Associate with id " + associateId + " already voted on this session");
        }

        Vote voteEntity = voteMapper.toVote(vote);
        voteEntity.setAssociate(associate);
        voteEntity.setVotingSession(votingSession);

        return voteMapper.toListVoteDto(voteRepository.save(voteEntity));
    }
}
