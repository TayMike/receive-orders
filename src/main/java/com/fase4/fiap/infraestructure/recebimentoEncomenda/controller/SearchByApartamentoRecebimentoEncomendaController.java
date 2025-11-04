package com.fase4.fiap.infraestructure.recebimentoEncomenda.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaPublicData;
import com.fase4.fiap.usecase.recebimentoEncomenda.SearchByApartamentoRecebimentoEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class SearchByApartamentoRecebimentoEncomendaController {

    private final SearchByApartamentoRecebimentoEncomendaUseCase searchByApartamentoRecebimentoEncomendaUseCase;

    public SearchByApartamentoRecebimentoEncomendaController(SearchByApartamentoRecebimentoEncomendaUseCase searchByApartamentoRecebimentoEncomendaUseCase) {
        this.searchByApartamentoRecebimentoEncomendaUseCase = searchByApartamentoRecebimentoEncomendaUseCase;
    }

    @GetMapping("/api/recebimento-encomendas/apartamento/{apartamentoId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecebimentoEncomendaPublicData> searchRecebimentoEncomenda(@PathVariable UUID apartamentoId) throws ApartamentoNotFoundException {
        List<RecebimentoEncomenda> recebimentoEncomenda = searchByApartamentoRecebimentoEncomendaUseCase.execute(apartamentoId);
        return recebimentoEncomenda.stream().map(RecebimentoEncomendaPublicData::new).toList();
    }

}