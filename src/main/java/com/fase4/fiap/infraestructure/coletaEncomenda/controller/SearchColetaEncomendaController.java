package com.fase4.fiap.infraestructure.coletaEncomenda.controller;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.infraestructure.coletaEncomenda.dto.ColetaEncomendaPublicData;
import com.fase4.fiap.usecase.coletaEncomenda.SearchColetaEncomendaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchColetaEncomendaController {

    private final SearchColetaEncomendaUseCase searchColetaEncomendaUseCase;

    public SearchColetaEncomendaController(SearchColetaEncomendaUseCase searchColetaEncomendaUseCase) {
        this.searchColetaEncomendaUseCase = searchColetaEncomendaUseCase;
    }

    @GetMapping("/api/coleta-encomendas")
    @ResponseStatus(HttpStatus.OK)
    public List<ColetaEncomendaPublicData> searchColetaEncomenda() {
        List<ColetaEncomenda> coletaEncomendas = this.searchColetaEncomendaUseCase.execute();
        return coletaEncomendas.stream().map(ColetaEncomendaPublicData::new).toList();
    }

}
