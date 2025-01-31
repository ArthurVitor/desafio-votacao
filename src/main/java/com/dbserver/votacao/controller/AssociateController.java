package com.dbserver.votacao.controller;

import com.dbserver.votacao.dto.Associate.CreateAssociateDto;
import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.service.AssociateService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/associates")
public class AssociateController {
    private final AssociateService associateService;

    public AssociateController(AssociateService associateService) {
        this.associateService = associateService;
    }

    @PostMapping()
    public ResponseEntity<ListAssociateDto> create(@RequestBody() CreateAssociateDto dto) {
        return ResponseEntity.ok(this.associateService.create(dto));
    }

    @GetMapping()
    public ResponseEntity<PageDto<ListAssociateDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(this.associateService.getAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<ListAssociateDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.associateService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.associateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
