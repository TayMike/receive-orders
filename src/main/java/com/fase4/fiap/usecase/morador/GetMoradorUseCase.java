package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class GetMoradorUseCase {

    private final MoradorGateway moradorGateway;

    public GetMoradorUseCase(MoradorGateway moradorGateway) {
        this.moradorGateway = moradorGateway;
    }

    @Transactional(readOnly = true)
    public Morador execute(UUID id) throws MoradorNotFoundException {
        return this.moradorGateway.findById(id).
                orElseThrow(() -> new MoradorNotFoundException("Morador not found: " + id));
    }

}
