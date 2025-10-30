package com.fase4.fiap.infraestructure.morador.gateway;

import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.MoradorRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.MoradorSchema;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MoradorDatabaseGateway implements MoradorGateway {

    private final MoradorRepository moradorRepository;

    public MoradorDatabaseGateway(MoradorRepository moradorRepository) {
        this.moradorRepository = moradorRepository;
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "moradores", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "morador", key = "#morador.id")}
    )
    public Morador save(Morador morador) {
        return moradorRepository.save(new MoradorSchema(morador)).toEntity();
    }

    @Override
    @Cacheable(value = "morador", key = "#id")
    public Optional<Morador> findById(UUID id) {
        return moradorRepository.findById(id).map(MoradorSchema::toEntity);
    }

    @Override
    public List<Morador> findAllByApartamentoId(UUID apartamentoId) {
        return moradorRepository.findAllByApartamentoId(apartamentoId).stream().map(MoradorSchema::toEntity).toList();
    }

    @Override
    @Cacheable(value = "moradores", key = "'moradores'")
    public List<Morador> findAll() {
        return moradorRepository.findAll().stream().map(MoradorSchema::toEntity).toList();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "moradores", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "morador", key = "#morador.id")}
    )
    public Morador update(Morador morador) {
        return moradorRepository.save(new MoradorSchema(morador)).toEntity();
    }

    @Override
    @CacheEvict(cacheNames = {"morador", "moradores"}, allEntries = true, beforeInvocation = true)
    public void deleteById(UUID id) {
        moradorRepository.deleteById(id);
    }

}
