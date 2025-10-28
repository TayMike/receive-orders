package com.fase4.fiap.infraestructure.apartamento.gateway;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.ApartamentoRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.ApartamentoSchema;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ApartamentoDatabaseGateway implements ApartamentoGateway {

    private final ApartamentoRepository apartamentoRepository;

    public ApartamentoDatabaseGateway(ApartamentoRepository apartamentoRepository) {
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "apartamentos", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "apartamento", key = "#apartamento.id")}
    )
    public Apartamento save(Apartamento apartamento) {
        return apartamentoRepository.save(new ApartamentoSchema(apartamento)).toEntity();
    }

    @Override
    @Cacheable(value = "apartamento", key = "#id")
    public Optional<Apartamento> findById(UUID id) {
        return apartamentoRepository.findById(id).map(ApartamentoSchema::toEntity);
    }

    @Override
    @Cacheable(value = "apartamentos", key = "'apartamentos'")
    public List<Apartamento> findAll() {
        return apartamentoRepository.findAll().stream().map(ApartamentoSchema::toEntity).toList();
    }

    @Override
    @CacheEvict(cacheNames = {"apartamento", "apartamentos"}, allEntries = true, beforeInvocation = true)
    public void deleteById(UUID id) {
        apartamentoRepository.deleteById(id);
    }

}
