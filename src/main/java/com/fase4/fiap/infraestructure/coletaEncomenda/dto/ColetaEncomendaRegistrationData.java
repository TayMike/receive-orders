package com.fase4.fiap.infraestructure.coletaEncomenda.dto;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ColetaEncomendaRegistrationData(
        UUID recebimentoEncomendaId,
        String cpfMoradorColeta,
        String nomeMoradorColeta,
        OffsetDateTime dataColeta
) implements IColetaEncomendaRegistrationData {

    public ColetaEncomendaRegistrationData(ColetaEncomenda coletaEncomenda) {
        this(
                coletaEncomenda.getRecebimentoEncomendaId(),
                coletaEncomenda.getCpfMoradorColeta(),
                coletaEncomenda.getNomeMoradorColeta(),
                coletaEncomenda.getDataColeta()
        );
    }

}