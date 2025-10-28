package com.fase4.fiap.entity.message.email.gateway;

import java.util.UUID;

public interface EmailGateway {

    void send(String to, String subject, String body);

    default void sendWithTracking(String to, String subject, String body, UUID notificacaoId, UUID moradorId) {
        send(to, subject, body, notificacaoId, moradorId);
    }

    void send(String to, String subject, String body, UUID notificacaoId, UUID moradorId);
}