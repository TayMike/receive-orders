package com.fase4.fiap.infraestructure.message.gateway;

import com.fase4.fiap.entity.message.notificacaoLeitura.gateway.NotificacaoLeituraGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.NotificacaoLeituraRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.NotificacaoLeituraSchema;

import java.util.Optional;
import java.util.UUID;

public class NotificacaoLeituraDatabaseGateway implements NotificacaoLeituraGateway {

    private final NotificacaoLeituraRepository notificacaoLeituraRepository;

    public NotificacaoLeituraDatabaseGateway(NotificacaoLeituraRepository notificacaoLeituraRepository) {
        this.notificacaoLeituraRepository = notificacaoLeituraRepository;
    }

    @Override
    public NotificacaoLeitura save(NotificacaoLeitura notificacaoLeitura) {
        return notificacaoLeituraRepository.save(new NotificacaoLeituraSchema(notificacaoLeitura)).toEntity();
    }

    @Override
    public Optional<NotificacaoLeitura> findById(UUID id) {
        return notificacaoLeituraRepository.findById(id).map(NotificacaoLeituraSchema::toEntity);
    }
}
