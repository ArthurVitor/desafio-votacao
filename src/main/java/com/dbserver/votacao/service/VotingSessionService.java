package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.VotingSession.CreateVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.exception.PautaNotFoundException;
import com.dbserver.votacao.mapper.VotingSessionMapper;
import com.dbserver.votacao.model.Pauta;
import com.dbserver.votacao.model.VotingSession;
import com.dbserver.votacao.repository.PautaRepository;
import com.dbserver.votacao.repository.VotingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service()
public class VotingSessionService {
    private final VotingSessionRepository votingSessionRepository;
    private final PautaRepository pautaRepository;
    private final VotingSessionMapper votingSessionMapper;

    public VotingSessionService(VotingSessionRepository votingSessionRepository, PautaRepository pautaRepository, VotingSessionMapper votingSessionMapper) {
        this.votingSessionRepository = votingSessionRepository;
        this.pautaRepository = pautaRepository;
        this.votingSessionMapper = votingSessionMapper;
    }

    @Transactional()
    public ListVotingSessionDto create(CreateVotingSessionDto dto, Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId).orElseThrow(() -> new PautaNotFoundException("Could not find pauta with id: " + pautaId));

        VotingSession votingSession = this.votingSessionMapper.toEntity(dto);
        votingSession.setPauta(pauta);
        votingSession.setEndDate(dto.endDate());

        VotingSession createdVotingSession = votingSessionRepository.save(votingSession);

        return this.votingSessionMapper.toDto(createdVotingSession);
    }

    public PageDto<ListVotingSessionDto> getAll(Pageable pageable) {
        Page<ListVotingSessionDto> page = this.votingSessionRepository.findAll(pageable).map(this.votingSessionMapper::toDto);

        return this.votingSessionMapper.toPageDto(page);
    }
}
