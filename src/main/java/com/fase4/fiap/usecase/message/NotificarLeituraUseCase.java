package com.fase4.fiap.usecase.message;

import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.gateway.NotificacaoLeituraGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificarLeituraUseCase {

    private final NotificacaoLeituraGateway notificacaoLeituraGateway;
    private final NotificacaoGateway notificacaoGateway;

    public NotificarLeituraUseCase(
            NotificacaoLeituraGateway notificacaoLeituraGateway,
            NotificacaoGateway notificacaoGateway) {
        this.notificacaoLeituraGateway = notificacaoLeituraGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    @Transactional
    public void execute(NotificacaoLeitura notificacaoLeitura) {
        if (notificacaoLeitura == null) {
            throw new IllegalArgumentException("NotificacaoLeitura não pode ser nula");
        }
        notificacaoLeituraGateway.save(notificacaoLeitura);

        notificacaoGateway.findById(notificacaoLeitura.getNotificacaoId()).ifPresent(notificacao -> {
            notificacao.marcarComoLida();
            notificacaoGateway.save(notificacao);
        });
    }
}