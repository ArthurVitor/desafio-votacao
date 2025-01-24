package com.dbserver.votacao.controller;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.service.PautaService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/pauta")
public class PautaController {
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping()
    public ResponseEntity<ListPautaDto> create(@RequestBody CreatePautaDto pauta) {
        return ResponseEntity.ok(this.pautaService.create(pauta));
    }

    @GetMapping()
    public ResponseEntity<PageDto<ListPautaDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(this.pautaService.getAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.pautaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListPautaDto> getById(@PathVariable() Long id) {
        return ResponseEntity.ok(this.pautaService.getById(id));
    }
}
