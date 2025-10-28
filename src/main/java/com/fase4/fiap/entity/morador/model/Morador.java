package com.fase4.fiap.entity.morador.model;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Morador extends AbstractEntity implements Serializable {

    @NonNull
    private String cpf;
    @NonNull
    private String nome;
    @NonNull
    private List<String> telefone;
    @NonNull
    private String email;
    @NonNull
    private List<UUID> apartamentoId;

    public Morador(@NonNull String cpf, @NonNull String nome, @NonNull List<String> telefone, @NonNull String email, @NonNull List<UUID> apartamentoId) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.apartamentoId = apartamentoId;
    }
}
