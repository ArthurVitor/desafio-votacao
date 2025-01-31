package com.dbserver.votacao.controller;

import com.dbserver.votacao.dto.Associate.ListAssociateVotesDto;
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

    @PostMapping("/{associateId}/{votingSessionId}")
    public ResponseEntity<ListVoteDto> create(@RequestBody CreateVoteDto vote, @PathVariable Long associateId, @PathVariable Long votingSessionId) {
        return ResponseEntity.ok(voteService.vote(vote, associateId, votingSessionId));
    }

    @DeleteMapping("/{associateId}/{votingSessionId}")
    public ResponseEntity<Void> remove(@PathVariable("associateId") Long associateId, @PathVariable("votingSessionId") Long votingSessionId) {
        this.voteService.remove(associateId, votingSessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{associateId}")
    public ResponseEntity<ListAssociateVotesDto> getAssociateVotes(@PathVariable("associateId") Long associateId) {
        return ResponseEntity.ok(voteService.getAssociateVotes(associateId));
    }
}
