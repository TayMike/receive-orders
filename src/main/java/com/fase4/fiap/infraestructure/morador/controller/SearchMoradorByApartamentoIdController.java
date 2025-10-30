package com.fase4.fiap.infraestructure.morador.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.infraestructure.morador.dto.MoradorPublicData;
import com.fase4.fiap.usecase.morador.SearchByApartamentoMoradorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class SearchMoradorByApartamentoIdController {

    private final SearchByApartamentoMoradorUseCase searchByApartamentoMoradorUseCase;

    public SearchMoradorByApartamentoIdController(SearchByApartamentoMoradorUseCase searchByApartamentoMoradorUseCase) {
        this.searchByApartamentoMoradorUseCase = searchByApartamentoMoradorUseCase;
    }

    @GetMapping("/api/moradores/apartamentos/{apartamentoId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MoradorPublicData> searchMoradorByApartamentoId(@PathVariable UUID apartamentoId) throws ApartamentoNotFoundException {
        List<Morador> insumos = this.searchByApartamentoMoradorUseCase.execute(apartamentoId);
        return insumos.stream().map(MoradorPublicData::new).toList();
    }

}
