package com.fase4.fiap.usecase.coletaEncomenda.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface IColetaEncomendaRegistrationData {

    UUID recebimentoEncomendaId();

    String cpfMoradorColeta();

    String nomeMoradorColeta();

    OffsetDateTime dataColeta();

}