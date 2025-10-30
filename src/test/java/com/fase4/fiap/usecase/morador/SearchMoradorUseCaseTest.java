package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchMoradorUseCaseTest {

    private MoradorGateway moradorGateway;
    private SearchMoradorUseCase useCase;

    @BeforeEach
    void setUp() {
        moradorGateway = mock(MoradorGateway.class);
        useCase = new SearchMoradorUseCase(moradorGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de moradors quando existirem registros")
    void shouldReturnListOfMoradorsWhenRecordsExist() {
        Morador morador = mock(Morador.class);
        when(moradorGateway.findAll()).thenReturn(List.of(morador));

        List<Morador> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(morador, resultado.getFirst());
        verify(moradorGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem registros de moradors")
    void shouldReturnEmptyListWhenNoMoradorsExist() {
        when(moradorGateway.findAll()).thenReturn(Collections.emptyList());

        List<Morador> resultado = useCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(moradorGateway, times(1)).findAll();
    }

}