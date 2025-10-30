package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetRecebimentoEncomendaUseCaseTest {

    private RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private GetRecebimentoEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        recebimentoEncomendaGateway = mock(RecebimentoEncomendaGateway.class);
        useCase = new GetRecebimentoEncomendaUseCase(recebimentoEncomendaGateway);
    }

    @Test
    @DisplayName("Deve retornar recebimentoEncomenda quando encontrado pelo id")
    void shouldReturnRecebimentoEncomendaWhenFoundById() throws RecebimentoEncomendaNotFoundException {
        UUID id = UUID.randomUUID();
        RecebimentoEncomenda recebimentoEncomenda = mock(RecebimentoEncomenda.class);

        when(recebimentoEncomendaGateway.findById(id)).thenReturn(Optional.of(recebimentoEncomenda));

        RecebimentoEncomenda resultado = useCase.execute(id);

        assertNotNull(resultado);
        assertEquals(recebimentoEncomenda, resultado);
        verify(recebimentoEncomendaGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar RecebimentoEncomendaNotFoundException quando recebimentoEncomenda não existe")
    void shouldThrowRecebimentoEncomendaNotFoundExceptionWhenRecebimentoEncomendaDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(recebimentoEncomendaGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecebimentoEncomendaNotFoundException.class, () -> useCase.execute(id));
        verify(recebimentoEncomendaGateway, times(1)).findById(id);
    }

}
