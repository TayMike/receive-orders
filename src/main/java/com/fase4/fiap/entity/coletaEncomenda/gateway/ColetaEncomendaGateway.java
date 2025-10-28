package com.fase4.fiap.entity.coletaEncomenda.gateway;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColetaEncomendaGateway {

    ColetaEncomenda save(ColetaEncomenda coletaEncomenda);
    Optional<ColetaEncomenda> findById(UUID id);
    List<ColetaEncomenda> findAll();

}
