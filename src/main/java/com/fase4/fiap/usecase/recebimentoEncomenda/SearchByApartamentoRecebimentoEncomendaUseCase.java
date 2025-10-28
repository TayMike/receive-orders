package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class SearchByApartamentoRecebimentoEncomendaUseCase {

    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;

    public SearchByApartamentoRecebimentoEncomendaUseCase(RecebimentoEncomendaGateway recebimentoEncomendaGateway) {
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
    }

    @Transactional(readOnly = true)
    public List<RecebimentoEncomenda> execute(UUID apartamentoId) {
        return this.recebimentoEncomendaGateway.findAllByApartamentoId(apartamentoId);
    }

}
