package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.exception.NotFoundException;
import com.dbserver.votacao.exception.PautaNotFoundException;
import com.dbserver.votacao.mapper.PageMapper;
import com.dbserver.votacao.mapper.PautaMapper;
import com.dbserver.votacao.model.Pauta;
import com.dbserver.votacao.repository.PautaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service()
public class PautaService {
    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public ListPautaDto create(CreatePautaDto dto) {
        Pauta pauta = PautaMapper.toPauta(dto);

        return PautaMapper.toDto(pautaRepository.save(pauta));
    }

    public PageDto<ListPautaDto> getAll(Pageable pageable) {
        Page<ListPautaDto> page = this.pautaRepository.findAll(pageable).map(PautaMapper::toDto);

        return PageMapper.toDto(page);
    }

    public void delete(Long id) {
        this.pautaRepository.deleteById(id);
    }

    public ListPautaDto getById(Long id) {
        return PautaMapper.toDto(this.pautaRepository.findById(id).orElseThrow(() -> new PautaNotFoundException("Couldn't find Pauta with Id: " + id)));
    }
}
