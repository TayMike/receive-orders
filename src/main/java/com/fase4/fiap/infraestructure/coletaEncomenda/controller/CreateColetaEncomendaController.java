package com.fase4.fiap.infraestructure.coletaEncomenda.controller;

import com.fase4.fiap.infraestructure.coletaEncomenda.dto.ColetaEncomendaPublicData;
import com.fase4.fiap.infraestructure.coletaEncomenda.dto.ColetaEncomendaRegistrationData;
import com.fase4.fiap.usecase.coletaEncomenda.CreateColetaEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateColetaEncomendaController {

    private final CreateColetaEncomendaUseCase createColetaEncomendaUseCase;

    public CreateColetaEncomendaController(CreateColetaEncomendaUseCase createColetaEncomendaUseCase) {
        this.createColetaEncomendaUseCase = createColetaEncomendaUseCase;
    }

    @PostMapping("/api/coleta-encomendas")
    @ResponseStatus(HttpStatus.CREATED)
    public ColetaEncomendaPublicData createColetaEncomenda(@RequestBody ColetaEncomendaRegistrationData dados) {
        return new ColetaEncomendaPublicData(createColetaEncomendaUseCase.execute(dados));
    }

}
