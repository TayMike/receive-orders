package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class SearchByApartamentoRecebimentoEncomendaUseCase {

    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private final ApartamentoGateway apartamentoGateway;

    public SearchByApartamentoRecebimentoEncomendaUseCase(RecebimentoEncomendaGateway recebimentoEncomendaGateway, ApartamentoGateway apartamentoGateway) {
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional(readOnly = true)
    public List<RecebimentoEncomenda> execute(UUID apartamentoId) throws ApartamentoNotFoundException {
        apartamentoGateway.findById(apartamentoId)
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + apartamentoId));

        return this.recebimentoEncomendaGateway.findAllByApartamentoId(apartamentoId);
    }

}
