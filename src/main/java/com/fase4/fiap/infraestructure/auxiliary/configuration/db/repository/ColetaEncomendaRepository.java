package com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository;

import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.ColetaEncomendaSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColetaEncomendaRepository extends JpaRepository<ColetaEncomendaSchema, UUID> {
}
