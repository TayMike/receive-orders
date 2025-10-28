package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteApartamentoUseCase {

    private final ApartamentoGateway apartamentoGateway;

    public DeleteApartamentoUseCase(ApartamentoGateway apartamentoGateway) {
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional
    public void execute(UUID id) throws ApartamentoNotFoundException {
        apartamentoGateway.findById(id)
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + id));

        apartamentoGateway.deleteById(id);
    }

}
