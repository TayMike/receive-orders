package com.fase4.fiap.infraestructure.apartamento.controller;

import com.fase4.fiap.infraestructure.apartamento.dto.ApartamentoPublicData;
import com.fase4.fiap.infraestructure.apartamento.dto.ApartamentoRegistrationData;
import com.fase4.fiap.usecase.apartamento.CreateApartamentoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateApartamentoController {

    private final CreateApartamentoUseCase createApartamentoUseCase;

    public CreateApartamentoController(CreateApartamentoUseCase createApartamentoUseCase) {
        this.createApartamentoUseCase = createApartamentoUseCase;
    }

    @PostMapping("/api/apartamentos")
    @ResponseStatus(HttpStatus.CREATED)
    public ApartamentoPublicData createApartamento(@RequestBody ApartamentoRegistrationData dados) {
        return new ApartamentoPublicData(createApartamentoUseCase.execute(dados));
    }

}
