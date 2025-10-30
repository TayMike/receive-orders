package com.fase4.fiap.infraestructure.morador.controller;

import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.infraestructure.morador.dto.MoradorPublicData;
import com.fase4.fiap.usecase.morador.GetMoradorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetMoradorController {

    private final GetMoradorUseCase getMoradorUseCase;

    public GetMoradorController(GetMoradorUseCase getMoradorUseCase) {
        this.getMoradorUseCase = getMoradorUseCase;
    }

    @GetMapping("/api/moradores/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MoradorPublicData getMorador(@PathVariable UUID id) throws MoradorNotFoundException {
        Morador morador = getMoradorUseCase.execute(id);
        return new MoradorPublicData(morador);
    }

}