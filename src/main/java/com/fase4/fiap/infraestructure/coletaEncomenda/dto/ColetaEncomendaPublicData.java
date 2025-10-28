package com.fase4.fiap.infraestructure.coletaEncomenda.dto;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaPublicData;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ColetaEncomendaPublicData(
        UUID id,
        UUID recebimentoEncomendaId,
        String cpfMoradorColeta,
        String nomeMoradorColeta,
        OffsetDateTime dataColeta
) implements IColetaEncomendaPublicData {

    public ColetaEncomendaPublicData(ColetaEncomenda coletaEncomenda) {
        this(
                coletaEncomenda.getId(),
                coletaEncomenda.getRecebimentoEncomendaId(),
                coletaEncomenda.getCpfMoradorColeta(),
                coletaEncomenda.getNomeMoradorColeta(),
                coletaEncomenda.getDataColeta()
        );
    }

}