package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchByApartamentoMoradorUseCaseTest {

    private MoradorGateway moradorGateway;
    private ApartamentoGateway apartamentoGateway;
    private SearchByApartamentoMoradorUseCase useCase;

    @BeforeEach
    void setUp() {
        moradorGateway = mock(MoradorGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new SearchByApartamentoMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de moradores quando existirem registros")
    void shouldReturnListOfMoradoresWhenRecordsExist() throws ApartamentoNotFoundException {
        Morador morador = mock(Morador.class);
        Apartamento apartamento = mock(Apartamento.class);
        when(apartamentoGateway.findById(apartamento.getId())).thenReturn(Optional.of(apartamento));
        when(moradorGateway.findAllByApartamentoId(apartamento.getId())).thenReturn(List.of(morador));

        List<Morador> resultado = useCase.execute(apartamento.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(morador, resultado.getFirst());
        verify(moradorGateway, times(1)).findAllByApartamentoId(apartamento.getId());
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldReturnEmptyListWhenNoMoradoresExist() {
        UUID apartamentoId = UUID.randomUUID();
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(apartamentoId));
        verify(apartamentoGateway, times(1)).findById(apartamentoId);
        verify(moradorGateway, times(0)).findAllByApartamentoId(apartamentoId);
    }

}