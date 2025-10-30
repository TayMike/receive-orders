package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchRecebimentoEncomendaUseCaseTest {

    private RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private SearchRecebimentoEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        recebimentoEncomendaGateway = mock(RecebimentoEncomendaGateway.class);
        useCase = new SearchRecebimentoEncomendaUseCase(recebimentoEncomendaGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de recebimentoEncomendas quando existirem registros")
    void shouldReturnListOfRecebimentoEncomendasWhenRecordsExist() {
        RecebimentoEncomenda recebimentoEncomenda = mock(RecebimentoEncomenda.class);
        when(recebimentoEncomendaGateway.findAll()).thenReturn(List.of(recebimentoEncomenda));

        List<RecebimentoEncomenda> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(recebimentoEncomenda, resultado.getFirst());
        verify(recebimentoEncomendaGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem registros de recebimentoEncomendas")
    void shouldReturnEmptyListWhenNoRecebimentoEncomendasExist() {
        when(recebimentoEncomendaGateway.findAll()).thenReturn(Collections.emptyList());

        List<RecebimentoEncomenda> resultado = useCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(recebimentoEncomendaGateway, times(1)).findAll();
    }

}