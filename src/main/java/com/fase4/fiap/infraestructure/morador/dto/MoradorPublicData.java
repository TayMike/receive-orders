package com.fase4.fiap.infraestructure.morador.dto;

import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorPublicData;

import java.util.List;
import java.util.UUID;

public record MoradorPublicData(
        UUID id,
        String cpf,
        String nome,
        List<String> telefone,
        List<UUID> apartamentoId
) implements IMoradorPublicData {

    public MoradorPublicData(Morador morador) {
        this(
                morador.getId(),
                morador.getCpf(),
                morador.getNome(),
                morador.getTelefone(),
                morador.getApartamentoId()
        );
    }

}
