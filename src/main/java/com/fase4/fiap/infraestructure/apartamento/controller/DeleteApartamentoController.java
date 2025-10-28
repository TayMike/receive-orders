package com.fase4.fiap.infraestructure.apartamento.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.usecase.apartamento.DeleteApartamentoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteApartamentoController {

    private final DeleteApartamentoUseCase deleteApartamentoUserCase;

    public DeleteApartamentoController(DeleteApartamentoUseCase deleteApartamentoUserCase) {
        this.deleteApartamentoUserCase = deleteApartamentoUserCase;
    }

    @DeleteMapping("/api/apartamentos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteApartamento(@PathVariable UUID id) throws ApartamentoNotFoundException {
        deleteApartamentoUserCase.execute(id);
    }

}