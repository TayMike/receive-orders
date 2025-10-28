package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class GetRecebimentoEncomendaUseCase {

    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;

    public GetRecebimentoEncomendaUseCase(RecebimentoEncomendaGateway recebimentoEncomendaGateway) {
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
    }

    @Transactional(readOnly = true)
    public RecebimentoEncomenda execute(UUID id) throws RecebimentoEncomendaNotFoundException {
        return this.recebimentoEncomendaGateway.findById(id).
                orElseThrow(() -> new RecebimentoEncomendaNotFoundException("Encomenda not found: " + id));
    }

}
