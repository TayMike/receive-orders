package com.fase4.fiap.entity.message.notificacao.gateway;

import com.fase4.fiap.entity.message.notificacao.model.Notificacao;

import java.util.Optional;
import java.util.UUID;

public interface NotificacaoGateway {
    void publish(Notificacao notificacao);
    Optional<Notificacao> findById(UUID id);
    void save(Notificacao notificacao);
}