package com.fase4.fiap.usecase.message.publish;

import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;

public class PublicarNotificacaoUseCase {

    private final NotificacaoGateway notificacaoGateway;

    public PublicarNotificacaoUseCase(NotificacaoGateway notificacaoGateway) {
        this.notificacaoGateway = notificacaoGateway;
    }

    public void publish(Notificacao notificacao) {
        notificacaoGateway.publish(notificacao);
    }

}