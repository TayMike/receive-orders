package com.fase4.fiap.infraestructure.apartamento.controller;

import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.infraestructure.apartamento.dto.ApartamentoPublicData;
import com.fase4.fiap.usecase.apartamento.SearchApartamentoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchApartamentoController {

    private final SearchApartamentoUseCase searchApartamentoUseCase;

    public SearchApartamentoController(SearchApartamentoUseCase searchApartamentoUseCase) {
        this.searchApartamentoUseCase = searchApartamentoUseCase;
    }

    @GetMapping("/api/apartamentos")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartamentoPublicData> searchApartamento() {
        List<Apartamento> insumos = this.searchApartamentoUseCase.execute();
        return insumos.stream().map(ApartamentoPublicData::new).toList();
    }

}