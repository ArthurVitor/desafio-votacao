package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.exception.NotFound.PautaNotFoundException;
import com.dbserver.votacao.mapper.PautaMapper;
import com.dbserver.votacao.model.Pauta;
import com.dbserver.votacao.repository.PautaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service()
public class PautaService {
    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    public PautaService(PautaRepository pautaRepository, PautaMapper pautaMapper) {
        this.pautaRepository = pautaRepository;
        this.pautaMapper = pautaMapper;
    }

    public ListPautaDto create(CreatePautaDto dto) {
        Pauta pauta = pautaMapper.toPauta(dto);

        return pautaMapper.toDto(pautaRepository.save(pauta));
    }

    public PageDto<ListPautaDto> getAll(Pageable pageable) {
        Page<ListPautaDto> page = this.pautaRepository.findAll(pageable).map(pautaMapper::toDto);

        return pautaMapper.toPageDto(page);
    }

    public void delete(Long id) {
        this.pautaRepository.deleteById(id);
    }

    public ListPautaDto getById(Long id) {
        return pautaMapper.toDto(this.pautaRepository.findById(id).orElseThrow(() -> new PautaNotFoundException("Couldn't find Pauta with Id: " + id)));
    }
}
