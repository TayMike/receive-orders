package com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository;

import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.NotificacaoLeituraSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificacaoLeituraRepository extends JpaRepository<NotificacaoLeituraSchema, UUID> {
}
