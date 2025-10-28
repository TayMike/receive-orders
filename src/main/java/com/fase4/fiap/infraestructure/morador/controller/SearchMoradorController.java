package com.fase4.fiap.infraestructure.morador.controller;

import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.infraestructure.morador.dto.MoradorPublicData;
import com.fase4.fiap.usecase.morador.SearchMoradorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchMoradorController {

    private final SearchMoradorUseCase searchMoradorUseCase;

    public SearchMoradorController(SearchMoradorUseCase searchMoradorUseCase) {
        this.searchMoradorUseCase = searchMoradorUseCase;
    }

    @GetMapping("/api/moradores")
    @ResponseStatus(HttpStatus.OK)
    public List<MoradorPublicData> searchMorador() {
        List<Morador> insumos = this.searchMoradorUseCase.execute();
        return insumos.stream().map(MoradorPublicData::new).toList();
    }

}