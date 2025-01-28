package com.dbserver.votacao.controller;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.VotingSession.CreateVotingSessionDto;
import com.dbserver.votacao.dto.VotingSession.ListVotingSessionDto;
import com.dbserver.votacao.service.VotingSessionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/voting-sessions")
public class VotingSessionController {
    private final VotingSessionService votingSessionService;

    public VotingSessionController(VotingSessionService votingSessionService) {
        this.votingSessionService = votingSessionService;
    }

    @PostMapping("/{pautaId}")
    public ResponseEntity<ListVotingSessionDto> create(@RequestBody() CreateVotingSessionDto dto, @PathVariable("pautaId") Long pautaId) {
        return ResponseEntity.ok(this.votingSessionService.create(dto, pautaId));
    }

    @GetMapping()
    public ResponseEntity<PageDto<ListVotingSessionDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(this.votingSessionService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListVotingSessionDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.votingSessionService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.votingSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
