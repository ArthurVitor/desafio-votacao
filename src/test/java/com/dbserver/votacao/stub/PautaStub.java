package com.dbserver.votacao.stub;

import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.model.Pauta;

import java.time.LocalDateTime;

public class PautaStub {
    public static CreatePautaDto generateValidCreatePautaDto() {
        return new CreatePautaDto("Reforma do Estatuto", "Discussão sobre mudanças no estatuto da associação");
    }

    public static Pauta generateValidPauta() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitle("Reforma do Estatuto");
        pauta.setDescription("Discussão sobre mudanças no estatuto da associação");
        pauta.setCreationDate(LocalDateTime.now());
        return pauta;
    }

    public static ListPautaDto generateValidListPautaDto() {
        return new ListPautaDto(1L, "Reforma do Estatuto", "Discussão sobre mudanças no estatuto da associação", LocalDateTime.now());
    }
}
