package com.fase4.fiap.infraestructure.morador.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.infraestructure.morador.dto.MoradorPublicData;
import com.fase4.fiap.infraestructure.morador.dto.MoradorUpdateData;
import com.fase4.fiap.usecase.morador.UpdateMoradorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UpdateMoradorController {

    private final UpdateMoradorUseCase updateMoradorUseCase;

    public UpdateMoradorController(UpdateMoradorUseCase updateMoradorUseCase) {
        this.updateMoradorUseCase = updateMoradorUseCase;
    }

    @PutMapping("/api/moradores/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MoradorPublicData updateMorador(@PathVariable UUID id, @RequestBody MoradorUpdateData dados) throws MoradorNotFoundException, ApartamentoNotFoundException {
        return new MoradorPublicData(updateMoradorUseCase.execute(id, dados));
    }

}
