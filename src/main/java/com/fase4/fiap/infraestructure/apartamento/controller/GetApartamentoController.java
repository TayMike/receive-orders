package com.fase4.fiap.infraestructure.apartamento.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.infraestructure.apartamento.dto.ApartamentoPublicData;
import com.fase4.fiap.usecase.apartamento.GetApartamentoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetApartamentoController {

    private final GetApartamentoUseCase getApartamentoUseCase;

    public GetApartamentoController(GetApartamentoUseCase getApartamentoUseCase) {
        this.getApartamentoUseCase = getApartamentoUseCase;
    }

    @GetMapping("/api/apartamentos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApartamentoPublicData getApartamento(@PathVariable UUID id) throws ApartamentoNotFoundException {
        Apartamento apartamento = getApartamentoUseCase.execute(id);
        return new ApartamentoPublicData(apartamento);
    }

}