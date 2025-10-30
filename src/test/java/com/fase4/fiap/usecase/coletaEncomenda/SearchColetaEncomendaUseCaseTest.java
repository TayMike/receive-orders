package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchColetaEncomendaUseCaseTest {

    private ColetaEncomendaGateway coletaEncomendaGateway;
    private SearchColetaEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        coletaEncomendaGateway = mock(ColetaEncomendaGateway.class);
        useCase = new SearchColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de coletaEncomendas quando existirem registros")
    void shouldReturnListOfColetaEncomendasWhenRecordsExist() {
        ColetaEncomenda coletaEncomenda = mock(ColetaEncomenda.class);
        when(coletaEncomendaGateway.findAll()).thenReturn(List.of(coletaEncomenda));

        List<ColetaEncomenda> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(coletaEncomenda, resultado.getFirst());
        verify(coletaEncomendaGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem registros de coletaEncomendas")
    void shouldReturnEmptyListWhenNoColetaEncomendasExist() {
        when(coletaEncomendaGateway.findAll()).thenReturn(Collections.emptyList());

        List<ColetaEncomenda> resultado = useCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(coletaEncomendaGateway, times(1)).findAll();
    }

}