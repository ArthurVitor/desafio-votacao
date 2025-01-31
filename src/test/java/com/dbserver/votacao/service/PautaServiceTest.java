package com.dbserver.votacao.service;

import com.dbserver.votacao.dto.Page.PageDto;
import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.exception.NotFound.PautaNotFoundException;
import com.dbserver.votacao.mapper.PautaMapper;
import com.dbserver.votacao.model.Pauta;
import com.dbserver.votacao.repository.PautaRepository;
import com.dbserver.votacao.stub.PautaStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Pauta Service")
public class PautaServiceTest {
    @Mock()
    private PautaRepository pautaRepository;

    @Mock()
    private PautaMapper pautaMapper;

    @InjectMocks()
    private PautaService pautaService;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Quando tentar criar uma Pauta com valores válidos, retornar o DTO de Listagem apropriado")
    public void createPauta_Successfully() {
        CreatePautaDto dto = PautaStub.generateValidCreatePautaDto();
        Pauta validPauta = PautaStub.generateValidPauta();
        ListPautaDto expectedDto = PautaStub.generateValidListPautaDto();

        when(pautaMapper.toPauta(any(CreatePautaDto.class))).thenReturn(validPauta);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(validPauta);
        when(pautaMapper.toDto(any(Pauta.class))).thenReturn(expectedDto);

        ListPautaDto result = pautaService.create(dto);

        assertEquals(expectedDto, result);
        verify(pautaMapper, times(1)).toPauta(dto);
        verify(pautaRepository, times(1)).save(validPauta);
        verify(pautaMapper, times(1)).toDto(validPauta);
    }

    @Test
    @DisplayName("Quando buscar todas as Pautas, retornar uma lista paginada de Pautas")
    public void getAllPautas_Successfully() {
        Pageable pageable = mock(Pageable.class);
        ListPautaDto pautaDto = PautaStub.generateValidListPautaDto();
        Page<ListPautaDto> page = new PageImpl<>(List.of(pautaDto));

        when(pautaRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(PautaStub.generateValidPauta())));
        when(pautaMapper.toDto(any(Pauta.class))).thenReturn(pautaDto);
        when(pautaMapper.toPageDto(any(Page.class))).thenReturn(new PageDto<>(page.getContent(), 0, 10, 1L, 1));

        PageDto<ListPautaDto> result = pautaService.getAll(pageable);

        assertEquals(1, result.content().size());
        assertEquals(pautaDto, result.content().getFirst());
        verify(pautaRepository, times(1)).findAll(pageable);
        verify(pautaMapper, times(1)).toDto(any(Pauta.class));
        verify(pautaMapper, times(1)).toPageDto(any(Page.class));
    }

    @Test
    @DisplayName("Quando buscar uma Pauta pelo ID, retornar o DTO correspondente")
    public void getPautaById_Successfully() {
        Long pautaId = 1L;
        Pauta pauta = PautaStub.generateValidPauta();
        ListPautaDto pautaDto = PautaStub.generateValidListPautaDto();

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(pautaMapper.toDto(pauta)).thenReturn(pautaDto);

        ListPautaDto result = pautaService.getById(pautaId);

        assertEquals(pautaDto, result);
        verify(pautaRepository, times(1)).findById(pautaId);
        verify(pautaMapper, times(1)).toDto(pauta);
    }

    @Test
    @DisplayName("Quando buscar uma Pauta com ID inexistente, lançar PautaNotFoundException")
    public void getPautaById_Fail() {
        Long invalidId = 99L;
        when(pautaRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PautaNotFoundException.class, () -> pautaService.getById(invalidId));

        verify(pautaRepository, times(1)).findById(invalidId);
        verify(pautaMapper, never()).toDto(any(Pauta.class));
    }

    @Test
    @DisplayName("Quando deletar uma Pauta existente, deve chamar o repositório corretamente")
    public void deletePauta_Successfully() {
        Long pautaId = 1L;
        doNothing().when(pautaRepository).deleteById(pautaId);

        pautaService.delete(pautaId);

        verify(pautaRepository, times(1)).deleteById(pautaId);
    }

    @Test
    @DisplayName("Quando tentar deletar uma Pauta inexistente, deve lançar PautaNotFoundException")
    public void deletePauta_Fail() {
        Long invalidId = 99L;
        doThrow(PautaNotFoundException.class).when(pautaRepository).deleteById(invalidId);

        assertThrows(PautaNotFoundException.class, () -> pautaService.delete(invalidId));

        verify(pautaRepository, times(1)).deleteById(invalidId);
    }
}
