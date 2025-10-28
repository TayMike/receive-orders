package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteMoradorUseCase {

    private final MoradorGateway moradorGateway;

    public DeleteMoradorUseCase(MoradorGateway moradorGateway) {
        this.moradorGateway = moradorGateway;
    }

    @Transactional
    public void execute(UUID id) throws MoradorNotFoundException {
        moradorGateway.findById(id)
                .orElseThrow(() -> new MoradorNotFoundException("Morador not found: " + id));

        moradorGateway.deleteById(id);
    }

}