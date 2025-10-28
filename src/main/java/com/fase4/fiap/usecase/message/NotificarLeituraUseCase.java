package com.fase4.fiap.usecase.message;

import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.gateway.NotificacaoLeituraGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
    public void execute(UUID notificacaoId, UUID moradorId, String ip, String userAgent) {
        NotificacaoLeitura leitura = new NotificacaoLeitura(notificacaoId, moradorId, ip, userAgent);
        notificacaoLeituraGateway.save(leitura);

        notificacaoGateway.findById(notificacaoId).ifPresent(notificacao -> {
            notificacao.marcarComoLida();
            notificacaoGateway.save(notificacao);
        });
    }
}