package com.fase4.fiap.infraestructure.morador.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.infraestructure.morador.dto.MoradorPublicData;
import com.fase4.fiap.infraestructure.morador.dto.MoradorRegistrationData;
import com.fase4.fiap.usecase.morador.CreateMoradorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateMoradorController {

    private final CreateMoradorUseCase createMoradorUseCase;

    public CreateMoradorController(CreateMoradorUseCase createMoradorUseCase) {
        this.createMoradorUseCase = createMoradorUseCase;
    }

    @PostMapping("/api/moradores")
    @ResponseStatus(HttpStatus.CREATED)
    public MoradorPublicData createMorador(@RequestBody MoradorRegistrationData dados) throws ApartamentoNotFoundException {
        return new MoradorPublicData(createMoradorUseCase.execute(dados));
    }

}