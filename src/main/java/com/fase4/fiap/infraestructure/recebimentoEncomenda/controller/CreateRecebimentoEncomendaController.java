package com.fase4.fiap.infraestructure.recebimentoEncomenda.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaPublicData;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaRegistrationData;
import com.fase4.fiap.usecase.recebimentoEncomenda.CreateRecebimentoEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateRecebimentoEncomendaController {

    private final CreateRecebimentoEncomendaUseCase createRecebimentoEncomendaUseCase;

    public CreateRecebimentoEncomendaController(CreateRecebimentoEncomendaUseCase createRecebimentoEncomendaUseCase) {
        this.createRecebimentoEncomendaUseCase = createRecebimentoEncomendaUseCase;
    }

    @PostMapping("/api/recebimento-encomendas")
    @ResponseStatus(HttpStatus.CREATED)
    public RecebimentoEncomendaPublicData createRecebimentoEncomenda(@RequestBody RecebimentoEncomendaRegistrationData dados) throws ApartamentoNotFoundException {
        return new RecebimentoEncomendaPublicData(createRecebimentoEncomendaUseCase.execute(dados));
    }

}