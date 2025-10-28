package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.coletaEncomenda.exception.ColetaEncomendaNotFoundException;
import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class GetColetaEncomendaUseCase {

    private final ColetaEncomendaGateway coletaEncomendaGateway;

    public GetColetaEncomendaUseCase(ColetaEncomendaGateway coletaEncomendaGateway) {
        this.coletaEncomendaGateway = coletaEncomendaGateway;
    }

    @Transactional(readOnly = true)
    public ColetaEncomenda execute(UUID id) throws ColetaEncomendaNotFoundException {
        return this.coletaEncomendaGateway.findById(id).
                orElseThrow(() -> new ColetaEncomendaNotFoundException("ColetaEncomenda not found: " + id));
    }

}
