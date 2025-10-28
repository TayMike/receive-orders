package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class GetApartamentoUseCase {

    private final ApartamentoGateway apartamentoGateway;

    public GetApartamentoUseCase(ApartamentoGateway apartamentoGateway) {
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional(readOnly = true)
    public Apartamento execute(UUID id) throws ApartamentoNotFoundException {
        return this.apartamentoGateway.findById(id).
                orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + id));
    }

}
