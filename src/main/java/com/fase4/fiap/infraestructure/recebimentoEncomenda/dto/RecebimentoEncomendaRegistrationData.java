package com.fase4.fiap.infraestructure.recebimentoEncomenda.dto;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaRegistrationData;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecebimentoEncomendaRegistrationData(
        UUID apartamentoId,
        String descricao,
        OffsetDateTime dataEntrega,
        RecebimentoEncomenda.EstadoColeta estadoColeta
) implements IRecebimentoEncomendaRegistrationData {

    public RecebimentoEncomendaRegistrationData(RecebimentoEncomenda recebimentoEncomenda) {
        this(
                recebimentoEncomenda.getApartamentoId(),
                recebimentoEncomenda.getDescricao(),
                recebimentoEncomenda.getDataEntrega(),
                recebimentoEncomenda.getEstadoColeta()
        );
    }
}
