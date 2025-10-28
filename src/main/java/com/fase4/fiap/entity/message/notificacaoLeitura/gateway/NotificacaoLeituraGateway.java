package com.fase4.fiap.entity.message.notificacaoLeitura.gateway;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacaoLeituraGateway {
    void registrarLeitura(UUID notificacaoId, UUID moradorId, String ip, String userAgent);
    NotificacaoLeitura save(NotificacaoLeitura notificacaoLeitura);
    Optional<NotificacaoLeitura> findById(UUID id);

}