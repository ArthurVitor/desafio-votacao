package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.VotingSession.CreateVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.exception.NotFound.VotingSessionNotFoundException;
import com.dbserver.votacao.mapper.VotingSessionMapper;
import com.dbserver.votacao.model.Pauta;
import com.dbserver.votacao.model.VotingSession;
import com.dbserver.votacao.repository.PautaRepository;
import com.dbserver.votacao.repository.VotingSessionRepository;
import com.dbserver.votacao.stub.PautaStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("VotingSession Service")
public class VotingSessionServiceTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotingSessionMapper votingSessionMapper;

    @InjectMocks
    private VotingSessionService votingSessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Quando criar uma sessão de votação com dados válidos, retorna o DTO da sessão criada")
    public void createVotingSession_Successfully() {
        Long pautaId = 1L;
        CreateVotingSessionDto dto = new CreateVotingSessionDto(LocalDateTime.now().plusMinutes(10));
        Pauta pauta = PautaStub.generateValidPauta(); // Simulação da pauta
        VotingSession votingSession = new VotingSession();
        votingSession.setId(1L);
        votingSession.setPauta(pauta);
        ListVotingSessionDto expectedDto = new ListVotingSessionDto(1L, PautaStub.generateValidListPautaDto(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), null);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votingSessionMapper.toEntity(dto)).thenReturn(votingSession);
        when(votingSessionMapper.toDto(votingSession)).thenReturn(expectedDto);
        when(votingSessionRepository.save(votingSession)).thenReturn(votingSession);

        ListVotingSessionDto result = votingSessionService.create(dto, pautaId);

        assertEquals(expectedDto, result);
        verify(pautaRepository, times(1)).findById(pautaId);
        verify(votingSessionRepository, times(1)).save(votingSession);
        verify(votingSessionMapper, times(1)).toDto(votingSession);
    }

    @Test
    @DisplayName("Quando buscar todas as sessões de votação, retorna uma lista paginada de sessões")
    public void getAllVotingSessions_Successfully() {
        Pageable pageable = mock(Pageable.class);
        ListVotingSessionDto sessionDto = new ListVotingSessionDto(1L, PautaStub.generateValidListPautaDto(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), null);
        Page<ListVotingSessionDto> page = new PageImpl<>(List.of(sessionDto));

        when(votingSessionRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(new VotingSession())));
        when(votingSessionMapper.toDto(any(VotingSession.class))).thenReturn(sessionDto);
        when(votingSessionMapper.toPageDto(any(Page.class))).thenReturn(new PageDto<>(page.getContent(), 0, 10, 1L, 1));

        PageDto<ListVotingSessionDto> result = votingSessionService.getAll(pageable);

        assertEquals(1, result.content().size());
        assertEquals(sessionDto, result.content().getFirst());
        verify(votingSessionRepository, times(1)).findAll(pageable);
        verify(votingSessionMapper, times(1)).toDto(any(VotingSession.class));
        verify(votingSessionMapper, times(1)).toPageDto(any(Page.class));
    }

    @Test
    @DisplayName("Quando buscar uma sessão de votação por ID, retorna o DTO correspondente")
    public void getVotingSessionById_Successfully() {
        Long votingSessionId = 1L;
        VotingSession votingSession = new VotingSession();
        votingSession.setId(1L);
        ListVotingSessionDto expectedDto = new ListVotingSessionDto(1L, PautaStub.generateValidListPautaDto(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), null);

        when(votingSessionRepository.findById(votingSessionId)).thenReturn(Optional.of(votingSession));
        when(votingSessionMapper.toDto(votingSession)).thenReturn(expectedDto);

        ListVotingSessionDto result = votingSessionService.getById(votingSessionId);

        assertEquals(expectedDto, result);
        verify(votingSessionRepository, times(1)).findById(votingSessionId);
        verify(votingSessionMapper, times(1)).toDto(votingSession);
    }

    @Test
    @DisplayName("Quando buscar uma sessão de votação com ID inexistente, lança VotingSessionNotFoundException")
    public void getVotingSessionById_Fail() {
        Long invalidId = 99L;
        when(votingSessionRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(VotingSessionNotFoundException.class, () -> votingSessionService.getById(invalidId));

        verify(votingSessionRepository, times(1)).findById(invalidId);
        verify(votingSessionMapper, never()).toDto(any(VotingSession.class));
    }

    @Test
    @DisplayName("Quando deletar uma sessão de votação existente, deve chamar o repositório corretamente")
    public void deleteVotingSession_Successfully() {
        Long votingSessionId = 1L;
        doNothing().when(votingSessionRepository).deleteById(votingSessionId);

        votingSessionService.delete(votingSessionId);

        verify(votingSessionRepository, times(1)).deleteById(votingSessionId);
    }

    @Test
    @DisplayName("Quando tentar deletar uma sessão de votação inexistente, deve lançar VotingSessionNotFoundException")
    public void deleteVotingSession_Fail() {
        Long invalidId = 99L;
        doThrow(VotingSessionNotFoundException.class).when(votingSessionRepository).deleteById(invalidId);

        assertThrows(VotingSessionNotFoundException.class, () -> votingSessionService.delete(invalidId));

        verify(votingSessionRepository, times(1)).deleteById(invalidId);
    }
}
