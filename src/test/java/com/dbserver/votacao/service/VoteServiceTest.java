package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Vote.CreateVoteDto;
import com.dbserver.votacao.dto.Vote.ListVoteDto;
import com.dbserver.votacao.enums.VoteOptionEnum;
import com.dbserver.votacao.exception.Conflict.AssociateAlreadyVotedException;
import com.dbserver.votacao.exception.NotFound.VotingSessionNotFoundException;
import com.dbserver.votacao.mapper.VoteMapper;
import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import com.dbserver.votacao.repository.AssociateRepository;
import com.dbserver.votacao.repository.VoteRepository;
import com.dbserver.votacao.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Vote Service Test")
public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private VoteMapper voteMapper;

    @InjectMocks
    private VoteService voteService;

    private Associate associate;
    private VotingSession votingSession;
    private CreateVoteDto createVoteDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        associate = new Associate(1L, "Associate 1", "111");
        votingSession = new VotingSession(1L, null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), null, null);
        createVoteDto = new CreateVoteDto(VoteOptionEnum.SIM);
    }

    @Test
    @DisplayName("Quando a votação for realizada com sucesso, retornar o DTO de ListVote")
    public void vote_Successfully() {
        Vote vote = new Vote(null, associate, votingSession, VoteOptionEnum.SIM);
        ListVoteDto expectedDto = new ListVoteDto(1L, null, null, VoteOptionEnum.SIM);

        when(associateRepository.findById(associate.getId())).thenReturn(Optional.of(associate));
        when(votingSessionRepository.findById(votingSession.getId())).thenReturn(Optional.of(votingSession));
        when(voteMapper.toVote(createVoteDto)).thenReturn(vote);
        when(voteRepository.save(vote)).thenReturn(vote);
        when(voteMapper.toListVoteDto(vote)).thenReturn(expectedDto);

        ListVoteDto result = voteService.vote(createVoteDto, associate.getId(), votingSession.getId());

        assertEquals(expectedDto, result);
        verify(voteRepository, times(1)).save(vote);
        verify(voteMapper, times(1)).toListVoteDto(vote);
    }

    @Test
    @DisplayName("Quando o associado já tiver votado, deve lançar a exceção AssociateAlreadyVotedException")
    public void vote_AssociateAlreadyVoted() {
        when(associateRepository.findById(associate.getId())).thenReturn(Optional.of(associate));
        when(votingSessionRepository.findById(votingSession.getId())).thenReturn(Optional.of(votingSession));
        when(voteRepository.existsByVotingSessionAndAssociate(votingSession, associate)).thenReturn(true);

        assertThrows(AssociateAlreadyVotedException.class, () -> voteService.vote(createVoteDto, associate.getId(), votingSession.getId()));

        verify(voteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Quando a sessão de votação não for encontrada, deve lançar VotingSessionNotFoundException")
    public void vote_VotingSessionNotFound() {
        when(associateRepository.findById(associate.getId())).thenReturn(Optional.of(associate));
        when(votingSessionRepository.findById(votingSession.getId())).thenReturn(Optional.empty());

        assertThrows(VotingSessionNotFoundException.class, () -> voteService.vote(createVoteDto, associate.getId(), votingSession.getId()));

        verify(voteRepository, never()).save(any());
    }
}
