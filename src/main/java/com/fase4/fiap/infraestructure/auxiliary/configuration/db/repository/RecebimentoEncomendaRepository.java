package com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository;

import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.RecebimentoEncomendaSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecebimentoEncomendaRepository extends JpaRepository<RecebimentoEncomendaSchema, UUID> {

    @NativeQuery(value = "SELECT * FROM recebimento_encomenda WHERE apartamento_id = :apartamentoId")
    List<RecebimentoEncomendaSchema> findAllByApartamentoId(@Param("apartamentoId") UUID apartamentoId);

}
