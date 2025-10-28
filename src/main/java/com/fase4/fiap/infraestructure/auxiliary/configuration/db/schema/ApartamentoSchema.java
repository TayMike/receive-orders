package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import com.fase4.fiap.entity.apartamento.model.Apartamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apartamento")
public class ApartamentoSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 1)
    @NotBlank(message = "Torre não pode ser nula")
    @Size(min = 1, max = 1, message = "Torre deve ter exatamente 1 caractere")
    private char torre;

    @Column(name = "numero", nullable = false)
    @NotNull(message = "Número não pode ser nulo")
    private byte numero;

    @Column(name = "andar", nullable = false)
    @NotNull(message = "Andar não pode ser nulo")
    private byte andar;

    public ApartamentoSchema(Apartamento apartamento) {
        this.id = apartamento.getId();
        this.torre = apartamento.getTorre();
        this.andar = apartamento.getAndar();
        this.numero = apartamento.getNumero();
    }

    public Apartamento toEntity() {
        Apartamento apartamento = new Apartamento(
                this.torre,
                this.andar,
                this.numero
        );

        apartamento.setId(this.getId());
        return apartamento;
    }

}
