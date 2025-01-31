package com.dbserver.votacao.controller;

import com.dbserver.votacao.dto.Cpf.ListCpf;
import com.dbserver.votacao.service.CpfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/cpf")
@RequiredArgsConstructor()
public class CpfController {
    private final CpfService cpfService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ListCpf> isCpfValid(@PathVariable("cpf") String cpf) {
        return ResponseEntity.ok(cpfService.isCpfValid(cpf));
    }
}
