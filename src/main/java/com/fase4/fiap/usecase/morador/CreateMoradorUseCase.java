package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorRegistrationData;
import org.springframework.transaction.annotation.Transactional;

public class CreateMoradorUseCase {

    private final MoradorGateway moradorGateway;
    private final ApartamentoGateway apartamentoGateway;

    public CreateMoradorUseCase(MoradorGateway moradorGateway, ApartamentoGateway apartamentoGateway) {
        this.moradorGateway = moradorGateway;
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional
    public Morador execute(IMoradorRegistrationData dados) throws ApartamentoNotFoundException {

        Apartamento apartamento = apartamentoGateway.findById(dados.apartamentoId().getFirst())
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + dados.apartamentoId().getFirst()));

        Morador morador = new Morador(dados.cpf(), dados.nome(), dados.telefone(), dados.email(),
                dados.apartamentoId());

        return this.moradorGateway.save(morador);
    }

}
