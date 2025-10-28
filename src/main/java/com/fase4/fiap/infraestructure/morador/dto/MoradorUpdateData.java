package com.fase4.fiap.infraestructure.morador.dto;

import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorUpdateData;

import java.util.List;
import java.util.UUID;

public record MoradorUpdateData(
        String nome,
        List<String> telefone,
        List<UUID> apartamentoId
) implements IMoradorUpdateData {

    public MoradorUpdateData(Morador morador) {
        this(
                morador.getNome(),
                morador.getTelefone(),
                morador.getApartamentoId()
        );
    }
}
