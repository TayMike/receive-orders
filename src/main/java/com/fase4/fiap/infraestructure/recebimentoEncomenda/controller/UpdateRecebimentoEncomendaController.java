package com.fase4.fiap.infraestructure.recebimentoEncomenda.controller;

import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaPublicData;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaUpdateData;
import com.fase4.fiap.usecase.recebimentoEncomenda.UpdateRecebimentoEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UpdateRecebimentoEncomendaController {

    private final UpdateRecebimentoEncomendaUseCase updateRecebimentoEncomendaUseCase;

    public UpdateRecebimentoEncomendaController(UpdateRecebimentoEncomendaUseCase updateRecebimentoEncomendaUseCase) {
        this.updateRecebimentoEncomendaUseCase = updateRecebimentoEncomendaUseCase;
    }

    @PutMapping("/api/recebimento-encomendas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecebimentoEncomendaPublicData updateRecebimentoEncomenda(@PathVariable UUID id, @RequestBody RecebimentoEncomendaUpdateData dados) throws RecebimentoEncomendaNotFoundException {
        return new RecebimentoEncomendaPublicData(updateRecebimentoEncomendaUseCase.execute(id, dados));
    }

}
