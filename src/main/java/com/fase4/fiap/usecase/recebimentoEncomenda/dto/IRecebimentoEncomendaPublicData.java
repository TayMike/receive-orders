package com.fase4.fiap.usecase.recebimentoEncomenda.dto;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface IRecebimentoEncomendaPublicData {

    UUID id();

    UUID apartamentoId();

    String descricao();

    OffsetDateTime dataEntrega();

    RecebimentoEncomenda.EstadoColeta estadoColeta();

}