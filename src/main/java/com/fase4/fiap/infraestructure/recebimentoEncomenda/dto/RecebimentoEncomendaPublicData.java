package com.fase4.fiap.infraestructure.recebimentoEncomenda.dto;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaPublicData;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecebimentoEncomendaPublicData(
        UUID id,
        UUID apartamentoId,
        String descricao,
        OffsetDateTime dataEntrega,
        RecebimentoEncomenda.EstadoColeta estadoColeta
) implements IRecebimentoEncomendaPublicData {

    public RecebimentoEncomendaPublicData(RecebimentoEncomenda recebimentoEncomenda) {
        this(
                recebimentoEncomenda.getId(),
                recebimentoEncomenda.getApartamentoId(),
                recebimentoEncomenda.getDescricao(),
                recebimentoEncomenda.getDataEntrega(),
                recebimentoEncomenda.getEstadoColeta()
        );
    }

}
