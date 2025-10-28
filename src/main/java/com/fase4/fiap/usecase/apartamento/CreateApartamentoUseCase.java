package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.usecase.apartamento.dto.IApartamentoRegistrationData;
import org.springframework.transaction.annotation.Transactional;

public class CreateApartamentoUseCase {

    private final ApartamentoGateway apartamentoGateway;

    public CreateApartamentoUseCase(ApartamentoGateway apartamentoGateway) {
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional
    public Apartamento execute(IApartamentoRegistrationData dados) {

        Apartamento apartamento = new Apartamento(dados.torre(), dados.andar(), dados.numero());

        return this.apartamentoGateway.save(apartamento);
    }

}
