package com.fase4.fiap.infraestructure.coletaEncomenda.gateway;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.ColetaEncomendaRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.ColetaEncomendaSchema;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ColetaEncomendaDatabaseGateway implements ColetaEncomendaGateway {

    private final ColetaEncomendaRepository coletaEncomendaRepository;

    public ColetaEncomendaDatabaseGateway(ColetaEncomendaRepository coletaEncomendaRepository) {
        this.coletaEncomendaRepository = coletaEncomendaRepository;
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "coletaEncomendas", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "coletaEncomenda", key = "#coletaEncomenda.id")}
    )
    public ColetaEncomenda save(ColetaEncomenda coletaEncomenda) {
        return coletaEncomendaRepository.save(new ColetaEncomendaSchema(coletaEncomenda)).toEntity();
    }

    @Override
    @Cacheable(value = "coletaEncomenda", key = "#id")
    public Optional<ColetaEncomenda> findById(UUID id) {
        return coletaEncomendaRepository.findById(id).map(ColetaEncomendaSchema::toEntity);
    }

    @Override
    @Cacheable(value = "coletaEncomendas", key = "'coletaEncomendas'")
    public List<ColetaEncomenda> findAll() {
        return coletaEncomendaRepository.findAll().stream().map(ColetaEncomendaSchema::toEntity).toList();
    }

}