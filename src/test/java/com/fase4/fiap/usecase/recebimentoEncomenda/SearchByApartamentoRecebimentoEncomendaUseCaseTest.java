package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchByApartamentoRecebimentoEncomendaUseCaseTest {

    private RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private ApartamentoGateway apartamentoGateway;
    private SearchByApartamentoRecebimentoEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        recebimentoEncomendaGateway = mock(RecebimentoEncomendaGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new SearchByApartamentoRecebimentoEncomendaUseCase(recebimentoEncomendaGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de recebimentoEncomendas quando existirem registros")
    void shouldReturnListOfRecebimentoEncomendaesWhenRecordsExist() throws ApartamentoNotFoundException {
        RecebimentoEncomenda recebimentoEncomenda = mock(RecebimentoEncomenda.class);
        Apartamento apartamento = mock(Apartamento.class);
        when(apartamentoGateway.findById(apartamento.getId())).thenReturn(Optional.of(apartamento));
        when(recebimentoEncomendaGateway.findAllByApartamentoId(apartamento.getId())).thenReturn(List.of(recebimentoEncomenda));

        List<RecebimentoEncomenda> resultado = useCase.execute(apartamento.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(recebimentoEncomenda, resultado.getFirst());
        verify(recebimentoEncomendaGateway, times(1)).findAllByApartamentoId(apartamento.getId());
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldReturnEmptyListWhenNoRecebimentoEncomendaesExist() {
        UUID apartamentoId = UUID.randomUUID();
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(apartamentoId));
        verify(apartamentoGateway, times(1)).findById(apartamentoId);
        verify(recebimentoEncomendaGateway, times(0)).findAllByApartamentoId(apartamentoId);
    }

}
