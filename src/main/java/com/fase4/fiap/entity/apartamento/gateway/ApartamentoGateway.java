package com.fase4.fiap.entity.apartamento.gateway;

import com.fase4.fiap.entity.apartamento.model.Apartamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApartamentoGateway {

    Apartamento save(Apartamento apartamento);
    Optional<Apartamento> findById(UUID id);
    List<Apartamento> findAll();
    void deleteById(UUID id);

}