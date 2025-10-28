package com.fase4.fiap.infraestructure.morador.dto;

import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorRegistrationData;

import java.util.List;
import java.util.UUID;

public record MoradorRegistrationData(
        String cpf,
        String nome,
        List<String> telefone,
        String email,
        List<UUID> apartamentoId
) implements IMoradorRegistrationData {

    public MoradorRegistrationData(Morador morador) {
        this(
                morador.getCpf(),
                morador.getNome(),
                morador.getTelefone(),
                morador.getEmail(),
                morador.getApartamentoId()
        );
    }
}