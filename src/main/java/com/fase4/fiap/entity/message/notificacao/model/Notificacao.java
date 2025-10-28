package com.fase4.fiap.entity.message.notificacao.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    private UUID id;
    private UUID apartamentoId;
    private String mensagem;
    private OffsetDateTime dataEnvio;
    private boolean lido = false;
    private UUID moradorId;

    public Notificacao(UUID apartamentoId, String mensagem, OffsetDateTime dataEnvio) {
        this.apartamentoId = apartamentoId;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }

    public void marcarComoLida() {
        this.lido = true;
    }
}