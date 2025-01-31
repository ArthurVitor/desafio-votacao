package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.VotingSession.CreateVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.enums.VoteOptionEnum;
import com.dbserver.votacao.enums.VotingSessionResultEnum;
import com.dbserver.votacao.exception.BadRequest.BadRequestException;
import com.dbserver.votacao.exception.NotFound.PautaNotFoundException;
import com.dbserver.votacao.exception.NotFound.VotingSessionNotFoundException;
import com.dbserver.votacao.mapper.VotingSessionMapper;
import com.dbserver.votacao.model.Pauta;
import com.dbserver.votacao.model.Vote;
import com.dbserver.votacao.model.VotingSession;
import com.dbserver.votacao.repository.PautaRepository;
import com.dbserver.votacao.repository.VoteRepository;
import com.dbserver.votacao.repository.VotingSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service()
@RequiredArgsConstructor()
public class VotingSessionService {
    private final VotingSessionRepository votingSessionRepository;
    private final PautaRepository pautaRepository;
    private final VotingSessionMapper votingSessionMapper;
    private final VoteRepository voteRepository;

    @Transactional
    public ListVotingSessionDto create(CreateVotingSessionDto dto, Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId).orElseThrow(() -> new PautaNotFoundException("Pauta not found with id: " + pautaId));

        VotingSession votingSession = votingSessionMapper.toEntity(dto);
        votingSession.setPauta(pauta);

        LocalDateTime endDate = dto.endDate();
        if (endDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("End date cannot be before the current date");
        }
        votingSession.setEndDate(endDate);

        VotingSession createdVotingSession = votingSessionRepository.save(votingSession);
        return votingSessionMapper.toDto(createdVotingSession);
    }

    public PageDto<ListVotingSessionDto> getAll(Pageable pageable) {
        Page<ListVotingSessionDto> page = this.votingSessionRepository.findAll(pageable).map(this.votingSessionMapper::toDto);

        return this.votingSessionMapper.toPageDto(page);
    }

    public ListVotingSessionDto getById(Long id) {
        VotingSession votingSession = this.votingSessionRepository.findById(id).orElseThrow(() -> new VotingSessionNotFoundException("Could not find voting session with id: " + id));

        return this.votingSessionMapper.toDto(votingSession);
    }

    public void delete(Long id) {
        this.votingSessionRepository.deleteById(id);
    }

    public void finishSession(VotingSession votingSession) {
        VotingSessionResultEnum sessionResult = this.getSessionResult(votingSession);

        votingSession.setResult(sessionResult);

        this.votingSessionRepository.save(votingSession);
    }

    private VotingSessionResultEnum getSessionResult(VotingSession votingSession) {
        List<Vote> votes = voteRepository.findByVotingSession(votingSession);

        if (votes.isEmpty()) {
            return VotingSessionResultEnum.INDEFINIDO;
        }

        Map<VoteOptionEnum, Long> votesCount = votes.stream()
                .collect(Collectors.groupingBy(Vote::getVoteOption, Collectors.counting()));

        long yesCount = votesCount.getOrDefault(VoteOptionEnum.SIM, (long) 0);
        long noCount = votesCount.getOrDefault(VoteOptionEnum.NAO, (long) 0);

        if (yesCount > noCount) {
            return VotingSessionResultEnum.APROVADA;
        } else if (noCount > yesCount) {
            return VotingSessionResultEnum.REPROVADA;
        } else {
            return VotingSessionResultEnum.INDEFINIDO;
        }
    }
}
