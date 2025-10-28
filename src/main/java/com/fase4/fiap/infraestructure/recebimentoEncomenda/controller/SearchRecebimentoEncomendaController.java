package com.fase4.fiap.infraestructure.recebimentoEncomenda.controller;

import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.dto.RecebimentoEncomendaPublicData;
import com.fase4.fiap.usecase.recebimentoEncomenda.SearchRecebimentoEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchRecebimentoEncomendaController {

    private final SearchRecebimentoEncomendaUseCase searchRecebimentoEncomendaUseCase;

    public SearchRecebimentoEncomendaController(SearchRecebimentoEncomendaUseCase searchRecebimentoEncomendaUseCase) {
        this.searchRecebimentoEncomendaUseCase = searchRecebimentoEncomendaUseCase;
    }

    @GetMapping("/api/recebimento-encomendas")
    @ResponseStatus(HttpStatus.OK)
    public List<RecebimentoEncomendaPublicData> searchRecebimentoEncomenda() {
        List<RecebimentoEncomenda> recebimentoEncomenda = this.searchRecebimentoEncomendaUseCase.execute();
        return recebimentoEncomenda.stream().map(RecebimentoEncomendaPublicData::new).toList();
    }

}
