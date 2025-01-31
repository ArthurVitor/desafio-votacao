package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Associate.CreateAssociateDto;
import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.mapper.AssociateMapper;
import com.dbserver.votacao.model.Associate;
import com.dbserver.votacao.repository.AssociateRepository;
import com.dbserver.votacao.stub.AssociateStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Associate Service")
public class AssociateServiceTest {
    @Mock()
    private AssociateRepository associateRepository;

    @Mock()
    private AssociateMapper associateMapper;

    @InjectMocks()
    private AssociateService associateService;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test()
    @DisplayName("Quando tentar criar um Associado com valores válidos, retornar o DTO de Listagem apropriado")
    public void createAssociate_Succesfully() {
        CreateAssociateDto dto = AssociateStub.generateValidCreateAssociateDto();
        Associate validAssociate = AssociateStub.generateValidAssociate();
        ListAssociateDto expectedAssociateDto = AssociateStub.generateValidListAssociateDto();

        when(associateMapper.toAssociate(any(CreateAssociateDto.class))).thenReturn(validAssociate);
        when(associateRepository.save(any(Associate.class))).thenReturn(validAssociate);
        when(associateMapper.toDto(any(Associate.class))).thenReturn(expectedAssociateDto);

        ListAssociateDto result = associateService.create(dto);

        assertEquals(expectedAssociateDto, result, "Erro ao tentar criar um Associado");
    }


}
