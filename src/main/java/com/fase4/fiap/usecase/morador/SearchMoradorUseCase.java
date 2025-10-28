package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SearchMoradorUseCase {

    private final MoradorGateway moradorGateway;

    public SearchMoradorUseCase(MoradorGateway moradorGateway) {
        this.moradorGateway = moradorGateway;
    }

    @Transactional(readOnly = true)
    public List<Morador> execute() {
        return this.moradorGateway.findAll();
    }

}
