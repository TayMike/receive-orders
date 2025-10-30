package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchApartamentoUseCaseTest {

    private ApartamentoGateway apartamentoGateway;
    private SearchApartamentoUseCase useCase;

    @BeforeEach
    void setUp() {
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new SearchApartamentoUseCase(apartamentoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de apartamentos quando existirem registros")
    void shouldReturnListOfApartamentosWhenRecordsExist() {
        Apartamento apartamento = mock(Apartamento.class);
        when(apartamentoGateway.findAll()).thenReturn(List.of(apartamento));

        List<Apartamento> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(apartamento, resultado.getFirst());
        verify(apartamentoGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem registros de apartamentos")
    void shouldReturnEmptyListWhenNoApartamentosExist() {
        when(apartamentoGateway.findAll()).thenReturn(Collections.emptyList());

        List<Apartamento> resultado = useCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(apartamentoGateway, times(1)).findAll();
    }

}
