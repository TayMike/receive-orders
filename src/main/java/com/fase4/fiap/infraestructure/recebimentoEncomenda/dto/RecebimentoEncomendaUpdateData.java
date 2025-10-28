package com.fase4.fiap.infraestructure.recebimentoEncomenda.dto;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaUpdateData;

public record RecebimentoEncomendaUpdateData(
        RecebimentoEncomenda.EstadoColeta estadoColeta
) implements IRecebimentoEncomendaUpdateData {

    public RecebimentoEncomendaUpdateData(RecebimentoEncomenda recebimentoEncomenda) {
        this(
                recebimentoEncomenda.getEstadoColeta()
        );
    }
}
