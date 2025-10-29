package com.fase4.fiap.infraestructure.message.gateway;

import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.NotificacaoRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.NotificacaoSchema;
import com.fase4.fiap.infraestructure.message.producer.NotificacaoProducer;

import java.util.Optional;
import java.util.UUID;

public class NotificacaoDatabaseGateway implements NotificacaoGateway {

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoProducer notificacaoProducer;

    public NotificacaoDatabaseGateway(NotificacaoRepository notificacaoRepository, NotificacaoProducer notificacaoProducer) {
        this.notificacaoRepository = notificacaoRepository;
        this.notificacaoProducer = notificacaoProducer;
    }

    public void publish(Notificacao notificacao) {
        notificacaoProducer.send(notificacao);
    }

    public Optional<Notificacao> findById(UUID id) {
        return notificacaoRepository.findById(id).map(NotificacaoSchema::toEntity);
    }

    public void save(Notificacao notificacao) {
        notificacaoRepository.save(new NotificacaoSchema(notificacao));
    }
}
