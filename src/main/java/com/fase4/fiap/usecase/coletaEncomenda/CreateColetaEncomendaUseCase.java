package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;
import org.springframework.transaction.annotation.Transactional;

public class CreateColetaEncomendaUseCase {

    private final ColetaEncomendaGateway coletaEncomendaGateway;

    public CreateColetaEncomendaUseCase(ColetaEncomendaGateway coletaEncomendaGateway) {
        this.coletaEncomendaGateway = coletaEncomendaGateway;
    }

    @Transactional
    public ColetaEncomenda execute(IColetaEncomendaRegistrationData dados) {

        ColetaEncomenda coletaEncomenda = new ColetaEncomenda(dados.recebimentoEncomendaId(), dados.cpfMoradorColeta(), dados.nomeMoradorColeta(), dados.dataColeta());

        return this.coletaEncomendaGateway.save(coletaEncomenda);
    }

}
