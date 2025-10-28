package com.fase4.fiap.infraestructure.message.consumer;

import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.usecase.message.subscribe.ProcessarNotificacaoUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoConsumer {

    private final ProcessarNotificacaoUseCase processarNotificacaoUseCase;

    public NotificacaoConsumer(ProcessarNotificacaoUseCase processarNotificacaoUseCase) {
        this.processarNotificacaoUseCase = processarNotificacaoUseCase;
    }

    @KafkaListener(topics = "notificacao-topic", groupId = "notificacao-group")
    public void consumir(Notificacao notificacao) {
        processarNotificacaoUseCase.execute(notificacao);
    }

}