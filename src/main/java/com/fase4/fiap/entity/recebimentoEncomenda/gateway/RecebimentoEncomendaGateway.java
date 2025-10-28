package com.fase4.fiap.entity.recebimentoEncomenda.gateway;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecebimentoEncomendaGateway {

    RecebimentoEncomenda save(RecebimentoEncomenda recebimentoEncomenda);
    Optional<RecebimentoEncomenda> findById(UUID id);
    List<RecebimentoEncomenda> findAllByApartamentoId(UUID ApartamentoId);
    List<RecebimentoEncomenda> findAll();
    RecebimentoEncomenda update(RecebimentoEncomenda recebimentoEncomenda);

}
