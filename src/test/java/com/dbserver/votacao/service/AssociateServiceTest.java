package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Associate.CreateAssociateDto;
import com.dbserver.votacao.dto.Associate.ListAssociateDto;
import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.exception.Conflict.AssociateAlreadyRegisteredException;
import com.dbserver.votacao.exception.NotFound.AssociateNotFoundException;
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
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @Test()
    @DisplayName("Quando tentar registrar um Associado com um CPF ja registrado, lançar exceção AssociateAlreadyRegisteredException")
    public void createAssociate_Fail() {
        CreateAssociateDto dto = AssociateStub.generateValidCreateAssociateDto();
        Associate validAssociate = AssociateStub.generateValidAssociate();

        when(associateMapper.toAssociate(any(CreateAssociateDto.class))).thenReturn(validAssociate);
        when(associateRepository.save(any(Associate.class))).thenThrow(AssociateAlreadyRegisteredException.class);

        assertThrows(AssociateAlreadyRegisteredException.class, () -> associateService.create(dto));
    }

    @Test()
    @DisplayName("Quando buscar todos os Associados, deve retornar uma PageDto com os dados corretamente")
    public void getAllAssociates_Successfully() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Associate associate = AssociateStub.generateValidAssociate();
        ListAssociateDto listAssociateDto = AssociateStub.generateValidListAssociateDto();
        Page<Associate> associatePage = new PageImpl<>(List.of(associate), pageable, 1);
        Page<ListAssociateDto> dtoPage = associatePage.map(a -> listAssociateDto);
        PageDto<ListAssociateDto> expectedPageDto = new PageDto<>(dtoPage.getContent(), 0, 10, 1L, 1);

        when(associateRepository.findAll(any(Pageable.class))).thenReturn(associatePage);
        when(associateMapper.toDto(any(Associate.class))).thenReturn(listAssociateDto);
        when(associateMapper.toPageDto(any(Page.class))).thenReturn(expectedPageDto);

        PageDto<ListAssociateDto> result = associateService.getAll(pageable);

        assertEquals(expectedPageDto, result);
    }

    @Test
    @DisplayName("Quando buscar um Associado por ID existente, deve retornar o DTO correto")
    public void getById_Successfully() {
        Long associateId = 1L;
        Associate associate = AssociateStub.generateValidAssociate();
        ListAssociateDto expectedDto = AssociateStub.generateValidListAssociateDto();

        when(associateRepository.findById(anyLong())).thenReturn(Optional.of(associate));
        when(associateMapper.toDto(associate)).thenReturn(expectedDto);

        ListAssociateDto result = associateService.getById(associateId);

        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Quando buscar um Associado por ID inexistente, deve lançar AssociateNotFoundException")
    public void getById_Fail() {
        Long invalidId = 99L;

        when(associateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AssociateNotFoundException.class, () -> associateService.getById(invalidId));
    }

    @Test
    @DisplayName("Quando deletar um Associado existente, deve chamar o repositório corretamente")
    public void deleteAssociate_Successfully() {
        Long associateId = 1L;
        doNothing().when(associateRepository).deleteById(associateId);

        associateService.delete(associateId);

        verify(associateRepository, times(1)).deleteById(associateId);
    }
}
