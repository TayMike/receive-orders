package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorUpdateData;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UpdateMoradorUseCase {

    private final MoradorGateway moradorGateway;
    private final ApartamentoGateway apartamentoGateway;

    public UpdateMoradorUseCase(MoradorGateway moradorGateway, ApartamentoGateway apartamentoGateway) {
        this.moradorGateway = moradorGateway;
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional
    public Morador execute(UUID id, IMoradorUpdateData dados) throws MoradorNotFoundException, ApartamentoNotFoundException {
        Morador morador = this.moradorGateway.findById(id)
                .orElseThrow(() -> new MoradorNotFoundException("Morador not found: " + id));

        Apartamento apartamento = apartamentoGateway.findById(dados.apartamentoId().getFirst())
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + dados.apartamentoId().getFirst()));

        if (dados.nome() != null && !dados.nome().isBlank())
            morador.setNome(dados.nome());

        if (!dados.telefone().isEmpty())
            morador.setTelefone(dados.telefone());

        if (dados.apartamentoId() != null) {
            morador.setApartamentoId(dados.apartamentoId());
        }

        return this.moradorGateway.update(morador);
    }

}