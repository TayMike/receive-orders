package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class SearchByApartamentoMoradorUseCase {

    private final MoradorGateway moradorGateway;
    private final ApartamentoGateway apartamentoGateway;

    public SearchByApartamentoMoradorUseCase(MoradorGateway moradorGateway, ApartamentoGateway apartamentoGateway) {
        this.moradorGateway = moradorGateway;
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional(readOnly = true)
    public List<Morador> execute(UUID apartamentoId) throws ApartamentoNotFoundException {
        apartamentoGateway.findById(apartamentoId)
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + apartamentoId));

        return this.moradorGateway.findAllByApartamentoId(apartamentoId);
    }

}
