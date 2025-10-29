package com.fase4.fiap.cache.core;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.infraestructure.apartamento.gateway.ApartamentoDatabaseGateway;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.ApartamentoRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.ApartamentoSchema;
import com.fase4.fiap.usecase.message.subscribe.ProcessarNotificacaoUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest(classes = ApartamentoDatabaseGatewayCacheTest.TestConfig.class)
@TestPropertySource(properties = {
        "spring.mail.host=localhost",
        "spring.mail.port=25",
        "spring.mail.username=test",
        "spring.mail.password=test",
        "spring.mail.properties.mail.smtp.auth=false",
        "spring.mail.properties.mail.smtp.starttls.enable=false",
        "app.url.base=http://localhost:8080"
})
@EnableCaching
class ApartamentoDatabaseGatewayCacheTest {

    @Autowired
    private ApartamentoGateway apartamentoGateway;

    @Autowired
    private CacheManager cacheManager;

    @MockitoBean
    private ApartamentoRepository apartamentoRepository;

    @MockitoBean
    private ProcessarNotificacaoUseCase processarNotificacaoUseCase;

    private Apartamento apartamento;

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {
        @org.springframework.context.annotation.Bean
        public CacheManager cacheManager() {
            return new org.springframework.cache.concurrent.ConcurrentMapCacheManager("apartamento", "apartamentos");
        }

        @org.springframework.context.annotation.Bean
        public ApartamentoGateway apartamentoGateway(ApartamentoRepository repository) {
            return new ApartamentoDatabaseGateway(repository);
        }
    }

    @BeforeEach
    void setUp() {
        // Limpa caches
        cacheManager.getCacheNames().forEach(name ->
                Optional.ofNullable(cacheManager.getCache(name)).ifPresent(Cache::clear)
        );

        apartamento = new Apartamento('C', (byte) 23, (byte) 10);
        apartamento.setId(UUID.randomUUID());
        log.debug("Apartamento de teste criado: torre={}, andar={}, numero={}, id={}",
                apartamento.getTorre(), apartamento.getAndar(), apartamento.getNumero(), apartamento.getId());
    }

    @Test
    void testApartamentoConstructorAndGetters() {
        assertNotNull(apartamento.getId(), "ID deve estar definido");
        assertEquals('C', apartamento.getTorre(), "Torre deve ser 'C'");
        assertEquals((byte) 23, apartamento.getAndar(), "Andar deve ser 23");
        assertEquals((byte) 10, apartamento.getNumero(), "Número deve ser 10");
    }

    @Test
    void testApartamentoSetters() {
        Apartamento novo = new Apartamento('A', (byte) 5, (byte) 101);
        novo.setId(UUID.randomUUID());

        assertNotNull(novo.getId(), "ID deve estar definido após setId");
        assertEquals('A', novo.getTorre(), "Torre deve ser 'A'");
        assertEquals((byte) 5, novo.getAndar(), "Andar deve ser 5");
        assertEquals((byte) 101, novo.getNumero(), "Número deve ser 101");
    }

    @Test
    void testApartamentoSerialization() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(apartamento);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Apartamento deserialized;
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            deserialized = (Apartamento) ois.readObject();
        }

        assertEquals(apartamento.getId(), deserialized.getId(), "ID serializado deve ser igual");
        assertEquals(apartamento.getTorre(), deserialized.getTorre(), "Torre deve ser igual");
        assertEquals(apartamento.getAndar(), deserialized.getAndar(), "Andar deve ser igual");
        assertEquals(apartamento.getNumero(), deserialized.getNumero(), "Número deve ser igual");
    }

    @Test
    void testApartamentoCacheIntegration() {
        ApartamentoSchema schema = new ApartamentoSchema(apartamento);

        when(apartamentoRepository.save(any(ApartamentoSchema.class))).thenReturn(schema);
        when(apartamentoRepository.findById(apartamento.getId())).thenReturn(Optional.of(schema));
        when(apartamentoRepository.findAll()).thenReturn(List.of(schema));

        // SAVE
        Apartamento saved = apartamentoGateway.save(apartamento);
        Cache aptCache = cacheManager.getCache("apartamento");
        Cache listCache = cacheManager.getCache("apartamentos");

        assertNotNull(aptCache);
        assertNotNull(listCache);

        Cache.ValueWrapper cachedApt = aptCache.get(apartamento.getId());
        assertNotNull(cachedApt, "Apartamento deve estar no cache 'apartamento'");
        assertEquals(saved, cachedApt.get());

        assertNull(listCache.get("apartamentos"), "Cache de lista deve estar vazio após save");

        // FIND ALL
        List<Apartamento> all = apartamentoGateway.findAll();
        Cache.ValueWrapper cachedList = listCache.get("apartamentos");
        assertNotNull(cachedList, "Lista deve estar no cache 'apartamentos'");
        assertEquals(all, cachedList.get());

        // FIND BY ID
        Optional<Apartamento> found = apartamentoGateway.findById(apartamento.getId());
        assertTrue(found.isPresent());
        assertEquals(saved, found.get());

        // DELETE
        apartamentoGateway.deleteById(apartamento.getId());
        assertNull(aptCache.get(apartamento.getId()), "Cache individual deve ser limpo");
        assertNull(listCache.get("apartamentos"), "Cache de lista deve ser limpo");

        verify(apartamentoRepository, times(1)).deleteById(apartamento.getId());
    }
}