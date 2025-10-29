package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "notificacao",
        indexes = {
                @Index(name = "idx_notificacao_apartamento", columnList = "apartamento_id"),
                @Index(name = "idx_notificacao_data_envio", columnList = "data_envio DESC"),
                @Index(name = "idx_notificacao_lido", columnList = "lido")
        }
)
@Getter @Setter @NoArgsConstructor
public class NotificacaoSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "apartamento_id", nullable = false)
    private UUID apartamentoId;

    @Column(nullable = false, length = 1000)
    private String mensagem;

    @Column(name = "data_envio", nullable = false)
    private OffsetDateTime dataEnvio;

    @Column(nullable = false)
    private boolean lido = false;

    public NotificacaoSchema(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.apartamentoId = notificacao.getApartamentoId();
        this.mensagem = notificacao.getMensagem();
        this.dataEnvio = notificacao.getDataEnvio();
        this.lido = notificacao.isLido();
    }

    public Notificacao toEntity() {
        Notificacao notificacao = new Notificacao(
                this.apartamentoId,
                this.mensagem,
                this.dataEnvio
        );
        notificacao.setId(this.id);
        if (this.lido) {
            notificacao.marcarComoLida();
        }
        return notificacao;
    }
}