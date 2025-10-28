package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
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
        name = "coleta_encomenda",
        indexes = {
                @Index(name = "idx_cpf_morador_coleta", columnList = "cpf_morador_coleta")
        }
)
public class ColetaEncomendaSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recebimento_encomenda_id", nullable = false)
    @NotNull(message = "Recebimento Encomenda ID não pode ser nulo")
    private UUID recebimentoEncomendaId;

    @Column(name = "cpf_morador_coleta", nullable = false, length = 100)
    @NotBlank(message = "CPF não pode estar vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas dígitos")
    private String cpfMoradorColeta;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nomeMoradorColeta;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @NotNull(message = "Data entrega não pode ser nula")
    @PastOrPresent(message = "Data entrega deve ser passada ou presente")
    private OffsetDateTime dataColeta;

    public ColetaEncomendaSchema(ColetaEncomenda coletaEncomenda) {
        this.id = coletaEncomenda.getRecebimentoEncomendaId();
        this.cpfMoradorColeta = coletaEncomenda.getCpfMoradorColeta();
        this.nomeMoradorColeta = coletaEncomenda.getNomeMoradorColeta();
        this.dataColeta = coletaEncomenda.getDataColeta();
    }

    public ColetaEncomenda toEntity() {
        ColetaEncomenda coletaEncomenda = new ColetaEncomenda(
                this.recebimentoEncomendaId,
                this.cpfMoradorColeta,
                this.nomeMoradorColeta,
                this.dataColeta
        );

        coletaEncomenda.setId(this.getId());
        return coletaEncomenda;
    }

}