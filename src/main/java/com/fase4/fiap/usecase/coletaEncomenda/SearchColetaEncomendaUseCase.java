package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SearchColetaEncomendaUseCase {

    private final ColetaEncomendaGateway coletaEncomendaGateway;

    public SearchColetaEncomendaUseCase(ColetaEncomendaGateway coletaEncomendaGateway) {
        this.coletaEncomendaGateway = coletaEncomendaGateway;
    }

    @Transactional(readOnly = true)
    public List<ColetaEncomenda> execute() {
        return this.coletaEncomendaGateway.findAll();
    }

}