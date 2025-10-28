package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SearchRecebimentoEncomendaUseCase {

    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;

    public SearchRecebimentoEncomendaUseCase(RecebimentoEncomendaGateway recebimentoEncomendaGateway) {
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
    }

    @Transactional(readOnly = true)
    public List<RecebimentoEncomenda> execute() {
        return this.recebimentoEncomendaGateway.findAll();
    }

}