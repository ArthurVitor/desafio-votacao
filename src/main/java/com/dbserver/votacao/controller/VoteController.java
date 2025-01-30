package com.dbserver.votacao.controller;

import com.dbserver.votacao.dto.Vote.CreateVoteDto;
import com.dbserver.votacao.dto.Vote.ListVoteDto;
import com.dbserver.votacao.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/vote")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{pautaId}/{associateId}")
    public ResponseEntity<ListVoteDto> create(@RequestBody CreateVoteDto vote, @PathVariable Long pautaId, @PathVariable Long associateId) {
        return ResponseEntity.ok(voteService.createVote(vote, pautaId, associateId));
    }
}
