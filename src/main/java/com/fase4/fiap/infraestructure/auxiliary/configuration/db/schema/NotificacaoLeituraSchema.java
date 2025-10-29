package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "notificacao_leitura",
        uniqueConstraints = @UniqueConstraint(columnNames = {"notificacao_id", "morador_id"}),
        indexes = {
                @Index(name = "idx_notificacao_leitura_notificacao", columnList = "notificacao_id"),
                @Index(name = "idx_notificacao_leitura_morador", columnList = "morador_id"),
                @Index(name = "idx_notificacao_leitura_lido_em", columnList = "lido_em DESC")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class NotificacaoLeituraSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "notificacao_id", nullable = false)
    private UUID notificacaoId;

    @Column(name = "morador_id", nullable = false)
    private UUID moradorId;

    @Column(name = "lido_em", nullable = false)
    private OffsetDateTime lidoEm;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    public NotificacaoLeituraSchema(NotificacaoLeitura leitura) {
        this.id = leitura.getId();
        this.notificacaoId = leitura.getNotificacaoId();
        this.moradorId = leitura.getMoradorId();
        this.lidoEm = leitura.getLidoEm();
        this.ipAddress = leitura.getIpAddress();
        this.userAgent = leitura.getUserAgent();
    }

    public NotificacaoLeitura toEntity() {
        NotificacaoLeitura leitura = new NotificacaoLeitura(
                this.notificacaoId,
                this.moradorId,
                this.ipAddress,
                this.userAgent
        );
        leitura.setLidoEm(this.lidoEm);
        if (this.id != null) {
            leitura.setId(this.id);
        }
        return leitura;
    }
}