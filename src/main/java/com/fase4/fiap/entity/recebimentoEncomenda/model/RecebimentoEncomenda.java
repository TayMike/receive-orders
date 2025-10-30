package com.fase4.fiap.entity.recebimentoEncomenda.model;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RecebimentoEncomenda extends AbstractEntity implements Serializable {

    @NonNull
    private UUID apartamentoId;

    @NonNull
    private String descricao;

    @NonNull
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @NonNull
    private EstadoColeta estadoColeta;

    public enum EstadoColeta {
        PENDENTE, COLETADA
    }

    public RecebimentoEncomenda(@NonNull UUID apartamentoId, @NonNull String descricao, @NonNull OffsetDateTime dataEntrega, @NonNull EstadoColeta estadoColeta) {
        this.apartamentoId = apartamentoId;
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
        this.estadoColeta = estadoColeta;
    }

    public static void validacaoDataEntrega(OffsetDateTime dataEntrega) {
        if (dataEntrega.isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data de entrega não pode ser no futuro.");
        }
    }

    public void atualizarEstadoColeta() {
        this.estadoColeta = EstadoColeta.COLETADA;
    }

}