package com.fase4.fiap.usecase.morador.dto;

import java.util.List;
import java.util.UUID;

public interface IMoradorRegistrationData {

    String cpf();

    String nome();

    List<String> telefone();

    String email();

    List<UUID> apartamentoId();

}
