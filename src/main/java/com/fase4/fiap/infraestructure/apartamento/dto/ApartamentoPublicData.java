package com.fase4.fiap.infraestructure.apartamento.dto;

import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.usecase.apartamento.dto.IApartamentoPublicData;

import java.util.UUID;

public record ApartamentoPublicData(
        UUID id,
        char torre,
        byte andar,
        byte numero
) implements IApartamentoPublicData {

    public ApartamentoPublicData(Apartamento apartamento) {
        this(
                apartamento.getId(),
                apartamento.getTorre(),
                apartamento.getAndar(),
                apartamento.getNumero()
        );
    }

}