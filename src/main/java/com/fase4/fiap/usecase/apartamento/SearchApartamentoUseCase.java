package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SearchApartamentoUseCase {

    private final ApartamentoGateway apartamentoGateway;

    public SearchApartamentoUseCase(ApartamentoGateway apartamentoGateway) {
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional(readOnly = true)
    public List<Apartamento> execute() {
        return this.apartamentoGateway.findAll();
    }

}
