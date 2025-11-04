package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaRegistrationData;
import org.springframework.transaction.annotation.Transactional;

import static com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda.validacaoDataEntrega;

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

        validacaoDataEntrega(dados.dataEntrega());

        apartamentoGateway.findById(dados.apartamentoId())
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + dados.apartamentoId()));

        RecebimentoEncomenda recebimentoEncomenda = new RecebimentoEncomenda(dados.apartamentoId(), dados.descricao(), dados.dataEntrega(), dados.estadoColeta());

        String mensagem = "Nova encomenda recebida: " +
                "\nDescrição: " +
                dados.descricao() +
                "\nData de Entrega: " +
                dados.dataEntrega().toString();
        // Send notification to the apartment user
        Notificacao notificacao = new Notificacao(
                dados.apartamentoId(),
                mensagem,
                dados.dataEntrega()
        );
        publicarNotificacaoUseCase.publish(notificacao);

        return this.recebimentoEncomendaGateway.save(recebimentoEncomenda);
    }

}