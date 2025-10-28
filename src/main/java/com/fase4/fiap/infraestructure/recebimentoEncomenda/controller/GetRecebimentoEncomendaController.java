package com.fase4.fiap.infraestructure.recebimentoEncomenda.controller;

import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaPublicData;
import com.fase4.fiap.usecase.recebimentoEncomenda.GetRecebimentoEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetRecebimentoEncomendaController {

    private final GetRecebimentoEncomendaUseCase getRecebimentoEncomendaUseCase;

    public GetRecebimentoEncomendaController(GetRecebimentoEncomendaUseCase getRecebimentoEncomendaUseCase) {
        this.getRecebimentoEncomendaUseCase = getRecebimentoEncomendaUseCase;
    }

    @GetMapping("/api/recebimento-encomendas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecebimentoEncomendaPublicData getRecebimentoEncomenda(@PathVariable UUID id) throws RecebimentoEncomendaNotFoundException {
        RecebimentoEncomenda recebimentoEncomenda = getRecebimentoEncomendaUseCase.execute(id);
        return new RecebimentoEncomendaPublicData(recebimentoEncomenda);
    }

}
