package com.fase4.fiap.infraestructure.message.producer;

import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoProducer {

    private final KafkaTemplate<String, Notificacao> kafkaTemplate;
    private static final String TOPIC = "notificacao-topic";

    public NotificacaoProducer(KafkaTemplate<String, Notificacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Notificacao notificacao) {
        kafkaTemplate.send(TOPIC, notificacao);
    }

}