package com.fase4.fiap.infraestructure.coletaEncomenda.controller;

import com.fase4.fiap.entity.coletaEncomenda.exception.ColetaEncomendaNotFoundException;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.infraestructure.coletaEncomenda.dto.ColetaEncomendaPublicData;
import com.fase4.fiap.usecase.coletaEncomenda.GetColetaEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetColetaEncomendaController {

    private final GetColetaEncomendaUseCase getColetaEncomendaUseCase;

    public GetColetaEncomendaController(GetColetaEncomendaUseCase getColetaEncomendaUseCase) {
        this.getColetaEncomendaUseCase = getColetaEncomendaUseCase;
    }

    @GetMapping("/api/coleta-encomendas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ColetaEncomendaPublicData getColetaEncomenda(@PathVariable UUID id) throws ColetaEncomendaNotFoundException {
        ColetaEncomenda coletaEncomenda = getColetaEncomendaUseCase.execute(id);
        return new ColetaEncomendaPublicData(coletaEncomenda);
    }

}