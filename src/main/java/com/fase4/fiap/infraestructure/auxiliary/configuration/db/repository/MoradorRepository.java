package com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository;

import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.MoradorSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MoradorRepository extends JpaRepository<MoradorSchema, UUID> {

    @Query("SELECT m FROM MoradorSchema m WHERE :apartamentoId MEMBER OF m.apartamentoId")
    List<MoradorSchema> findAllByApartamentoId(@Param("apartamentoId") UUID apartamentoId);

}
