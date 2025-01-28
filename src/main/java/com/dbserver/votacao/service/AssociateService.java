package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Associate.CreateAssociateDto;
import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.exception.AssociateNotFoundException;
import com.dbserver.votacao.mapper.AssociateMapper;
import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.repository.AssociateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service()
public class AssociateService {
    private final AssociateRepository associateRepository;

    private final AssociateMapper associateMapper;

    public AssociateService(AssociateRepository associateRepository, AssociateMapper associateMapper) {
        this.associateRepository = associateRepository;
        this.associateMapper = associateMapper;
    }

    @Transactional()
    public ListAssociateDto create(CreateAssociateDto dto) {
        Associate associate = associateMapper.toAssociate(dto);

        return associateMapper.toDto(associateRepository.save(associate));
    }

    public PageDto<ListAssociateDto> getAll(Pageable pageable) {
        Page<ListAssociateDto> page = this.associateRepository.findAll(pageable).map(associateMapper::toDto);

        return associateMapper.toPageDto(page);
    }

    public ListAssociateDto getById(Long id) {
        return associateMapper.toDto(this.associateRepository.findById(id).orElseThrow(() -> new AssociateNotFoundException("Associate not found with id " + id)));
    }

    public void delete(Long id) {
        this.associateRepository.deleteById(id);
    }
}
