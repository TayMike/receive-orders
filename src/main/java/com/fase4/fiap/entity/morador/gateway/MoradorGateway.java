package com.fase4.fiap.entity.morador.gateway;

import com.fase4.fiap.entity.morador.model.Morador;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MoradorGateway {

    Morador save(Morador morador);
    Optional<Morador> findById(UUID id);
    List<Morador> findAllByApartamentoId(UUID apartamentoId);
    List<Morador> findAll();
    Morador update(Morador morador);
    void deleteById(UUID id);

}
