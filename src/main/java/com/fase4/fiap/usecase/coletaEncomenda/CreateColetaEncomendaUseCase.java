package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;
import org.springframework.transaction.annotation.Transactional;

import static com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda.validacaoDataColeta;

public class CreateColetaEncomendaUseCase {

    private final ColetaEncomendaGateway coletaEncomendaGateway;
    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;

    public CreateColetaEncomendaUseCase(ColetaEncomendaGateway coletaEncomendaGateway, RecebimentoEncomendaGateway recebimentoEncomendaGateway) {
        this.coletaEncomendaGateway = coletaEncomendaGateway;
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
    }

    @Transactional
    public ColetaEncomenda execute(IColetaEncomendaRegistrationData dados) throws RecebimentoEncomendaNotFoundException {

        validacaoDataColeta(dados.dataColeta());

        RecebimentoEncomenda recebimentoEncomenda = recebimentoEncomendaGateway.findById(dados.recebimentoEncomendaId())
                .orElseThrow(() -> new RecebimentoEncomendaNotFoundException("RecebimentoEncomenda not found: " + dados.recebimentoEncomendaId()));

        ColetaEncomenda coletaEncomenda = new ColetaEncomenda(dados.recebimentoEncomendaId(), dados.cpfMoradorColeta(), dados.nomeMoradorColeta(), dados.dataColeta());

        recebimentoEncomenda.atualizarEstadoColeta();

        return this.coletaEncomendaGateway.save(coletaEncomenda);
    }

}