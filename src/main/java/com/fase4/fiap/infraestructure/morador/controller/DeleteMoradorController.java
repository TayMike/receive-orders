package com.fase4.fiap.infraestructure.morador.controller;

import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.usecase.morador.DeleteMoradorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteMoradorController {

    private final DeleteMoradorUseCase deleteMoradorUserCase;

    public DeleteMoradorController(DeleteMoradorUseCase deleteMoradorUserCase) {
        this.deleteMoradorUserCase = deleteMoradorUserCase;
    }

    @DeleteMapping("/api/moradores/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMorador(@PathVariable UUID id) throws MoradorNotFoundException {
        deleteMoradorUserCase.execute(id);
    }

}