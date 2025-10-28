package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaRegistrationData;
import org.springframework.transaction.annotation.Transactional;

public class CreateRecebimentoEncomendaUseCase {

    private final RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private final ApartamentoGateway apartamentoGateway;
    private final PublicarNotificacaoUseCase publicarNotificacaoUseCase;

    public CreateRecebimentoEncomendaUseCase(RecebimentoEncomendaGateway recebimentoEncomendaGateway, ApartamentoGateway apartamentoGateway, PublicarNotificacaoUseCase publicarNotificacaoUseCase) {
        this.recebimentoEncomendaGateway = recebimentoEncomendaGateway;
        this.apartamentoGateway = apartamentoGateway;
        this.publicarNotificacaoUseCase = publicarNotificacaoUseCase;
    }

    @Transactional
    public RecebimentoEncomenda execute(IRecebimentoEncomendaRegistrationData dados) throws ApartamentoNotFoundException {

        apartamentoGateway.findById(dados.apartamentoId())
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + dados.apartamentoId()));

        RecebimentoEncomenda recebimentoEncomenda = new RecebimentoEncomenda(dados.apartamentoId(), dados.descricao(), dados.dataEntrega(), dados.estadoColeta());

        // Send notification to the apartment user
        Notificacao notificacao = new Notificacao(
                dados.apartamentoId(),
                "Nova encomenda recebida",
                dados.dataEntrega()
        );
        publicarNotificacaoUseCase.publish(notificacao);

        return this.recebimentoEncomendaGateway.save(recebimentoEncomenda);
    }

}