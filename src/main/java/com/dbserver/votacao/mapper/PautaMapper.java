package com.dbserver.votacao.mapper;

import com.dbserver.votacao.dto.Pauta.CreatePautaDto;
import com.dbserver.votacao.dto.Pauta.ListPautaDto;
import com.dbserver.votacao.model.Pauta;

public class PautaMapper {
    public static Pauta toPauta(CreatePautaDto dto) {
        Pauta pauta = new Pauta();
        pauta.setTitle(dto.title());
        pauta.setDescription(dto.description());
        pauta.setActive(dto.active());
        pauta.setLifeTime(dto.lifeTime());
        return pauta;
    }

    public static ListPautaDto toDto(Pauta pauta) {
        return new ListPautaDto(pauta.getId(), pauta.getTitle(), pauta.getDescription(), pauta.getActive(), pauta.getLifeTime());
    }
}
