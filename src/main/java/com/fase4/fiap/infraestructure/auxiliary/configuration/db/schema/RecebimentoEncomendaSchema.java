package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda.EstadoColeta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "recebimento_encomenda",
        indexes = {
                @Index(name = "idx_apartamento_id", columnList = "apartamento_id"),
                @Index(name = "idx_data_entrega", columnList = "data_entrega DESC"),
                @Index(name = "idx_estado_coleta", columnList = "estado_coleta")
        }
)
public class RecebimentoEncomendaSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "apartamento_id", nullable = false)
    @NotNull(message = "Apartamento ID não pode ser nulo")
    private UUID apartamentoId;

    @Column(nullable = false)
    @NotBlank(message = "Descrição não pode estar vazio")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @Column(name = "data_entrega", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @NotNull(message = "Data entrega não pode ser nula")
    @PastOrPresent(message = "Data entrega deve ser passada ou presente")
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_coleta", nullable = false)
    @NotNull(message = "Estado de coleta não pode ser nulo")
    private EstadoColeta estadoColeta;

    public RecebimentoEncomendaSchema(RecebimentoEncomenda recebimentoEncomenda) {
        this.id = recebimentoEncomenda.getId();
        this.apartamentoId = recebimentoEncomenda.getApartamentoId();
        this.descricao = recebimentoEncomenda.getDescricao();
        this.dataEntrega = recebimentoEncomenda.getDataEntrega();
        this.estadoColeta = recebimentoEncomenda.getEstadoColeta();
    }

    public RecebimentoEncomenda toEntity() {
        RecebimentoEncomenda recebimentoEncomenda = new RecebimentoEncomenda(
                this.apartamentoId,
                this.descricao,
                this.dataEntrega,
                this.estadoColeta
        );
        recebimentoEncomenda.setId(this.id);
        return recebimentoEncomenda;
    }
}