package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaUpdateData;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UpdateRecebimentoEncomendaUseCase {

    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;

    public UpdateRecebimentoEncomendaUseCase(RecebimentoEncomendaGateway recebimentoEncomendaGateway) {
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
    }

    @Transactional
    public RecebimentoEncomenda execute(UUID id, IRecebimentoEncomendaUpdateData dados) throws RecebimentoEncomendaNotFoundException {
        RecebimentoEncomenda recebimentoEncomenda = this.recebimentoEncomendaGateway.findById(id)
                .orElseThrow(() -> new RecebimentoEncomendaNotFoundException("Encomenda not found: " + id));

        if (dados.estadoColeta() != null)
            recebimentoEncomenda.setEstadoColeta(dados.estadoColeta());

        return this.recebimentoEncomendaGateway.update(recebimentoEncomenda);
    }

}
