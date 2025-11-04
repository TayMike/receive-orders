package com.fase4.fiap.infraestructure.recebimentoEncomenda.gateway;

import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.RecebimentoEncomendaRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.RecebimentoEncomendaSchema;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecebimentoEncomendaDatabaseGateway implements RecebimentoEncomendaGateway {

    private final RecebimentoEncomendaRepository recebimentoEncomendaRepository;

    public RecebimentoEncomendaDatabaseGateway(RecebimentoEncomendaRepository recebimentoEncomendaRepository) {
        this.recebimentoEncomendaRepository = recebimentoEncomendaRepository;
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "recebimentoEncomendas", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "recebimentoEncomenda", key = "#recebimentoEncomenda.id")}
    )
    public RecebimentoEncomenda save(RecebimentoEncomenda recebimentoEncomenda) {
        return recebimentoEncomendaRepository.save(new RecebimentoEncomendaSchema(recebimentoEncomenda)).toEntity();
    }

    @Override
    @Cacheable(value = "recebimentoEncomenda", key = "#id")
    public Optional<RecebimentoEncomenda> findById(UUID id) {
        return recebimentoEncomendaRepository.findById(id).map(RecebimentoEncomendaSchema::toEntity);
    }

    @Override
    @Cacheable(value = "recebimentoEncomendasByApartamentoId", key = "#id")
    public List<RecebimentoEncomenda> findAllByApartamentoId(UUID id) {
        return recebimentoEncomendaRepository.findAllByApartamentoId(id).stream().map(RecebimentoEncomendaSchema::toEntity).toList();
    }

    @Override
    @Cacheable(value = "recebimentoEncomendas", key = "'recebimentoEncomendas'")
    public List<RecebimentoEncomenda> findAll() {
        return recebimentoEncomendaRepository.findAll().stream().map(RecebimentoEncomendaSchema::toEntity).toList();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "recebimentoEncomendas", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "recebimentoEncomenda", key = "#recebimentoEncomenda.id")}
    )
    public RecebimentoEncomenda update(RecebimentoEncomenda recebimentoEncomenda) {
        return recebimentoEncomendaRepository.save(new RecebimentoEncomendaSchema(recebimentoEncomenda)).toEntity();
    }

}
