package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import com.fase4.fiap.entity.morador.model.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "morador",
        indexes = @Index(name = "idx_morador_cpf", columnList = "cpf")
)
public class MoradorSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 11, unique = true)
    @NotBlank(message = "CPF não pode estar vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    private String cpf;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @ElementCollection
    @CollectionTable(
            name = "morador_telefones",
            joinColumns = @JoinColumn(name = "morador_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"morador_id", "telefone"})
    )
    @Column(name = "telefone", nullable = false, length = 20)
    @Size(min = 1, message = "Deve haver pelo menos um telefone")
    private List<@Pattern(regexp = "\\d{10,15}", message = "Telefone inválido") String> telefone = new ArrayList<>();

    @Column(nullable = false, length = 100)
    @NotBlank(message = "E-mail não pode estar vazio")
    @Email(message = "E-mail inválido")
    @Size(max = 100)
    private String email;

    @ElementCollection
    @CollectionTable(
            name = "morador_apartamentos",
            joinColumns = @JoinColumn(name = "morador_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"morador_id", "apartamento_ids"})
    )
    @Column(name = "apartamento_ids", nullable = false)
    @Size(min = 1, message = "Deve haver pelo menos um apartamento")
    private List<UUID> apartamentoId = new ArrayList<>();

    public MoradorSchema(Morador morador) {
        this.id = morador.getId();
        this.cpf = morador.getCpf();
        this.nome = morador.getNome();
        this.telefone = new ArrayList<>(morador.getTelefone());
        this.email = morador.getEmail();
        this.apartamentoId = new ArrayList<>(morador.getApartamentoId());
    }

    public Morador toEntity() {
        Morador morador = new Morador(
                this.cpf,
                this.nome,
                new ArrayList<>(this.telefone),
                this.email,
                new ArrayList<>(this.apartamentoId)
        );
        morador.setId(this.id);
        return morador;
    }
}