package com.fase4.fiap.entity.message.notificacaoLeitura.gateway;

import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;

import java.util.Optional;
import java.util.UUID;

public interface NotificacaoLeituraGateway {

    NotificacaoLeitura save(NotificacaoLeitura notificacaoLeitura);

    Optional<NotificacaoLeitura> findById(UUID id);

}