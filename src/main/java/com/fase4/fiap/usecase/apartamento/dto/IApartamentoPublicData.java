package com.fase4.fiap.usecase.apartamento.dto;

import java.util.UUID;

public interface IApartamentoPublicData {

    UUID id();

    char torre();

    byte andar();

    byte numero();

}
