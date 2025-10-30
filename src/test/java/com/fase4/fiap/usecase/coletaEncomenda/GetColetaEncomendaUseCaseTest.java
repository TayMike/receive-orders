package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.coletaEncomenda.exception.ColetaEncomendaNotFoundException;
import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetColetaEncomendaUseCaseTest {

    private ColetaEncomendaGateway coletaEncomendaGateway;
    private GetColetaEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        coletaEncomendaGateway = mock(ColetaEncomendaGateway.class);
        useCase = new GetColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    @Test
    @DisplayName("Deve retornar coletaEncomenda quando encontrado pelo id")
    void shouldReturnColetaEncomendaWhenFoundById() throws ColetaEncomendaNotFoundException {
        UUID id = UUID.randomUUID();
        ColetaEncomenda coletaEncomenda = mock(ColetaEncomenda.class);

        when(coletaEncomendaGateway.findById(id)).thenReturn(Optional.of(coletaEncomenda));

        ColetaEncomenda resultado = useCase.execute(id);

        assertNotNull(resultado);
        assertEquals(coletaEncomenda, resultado);
        verify(coletaEncomendaGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar ColetaEncomendaNotFoundException quando coletaEncomenda não existe")
    void shouldThrowColetaEncomendaNotFoundExceptionWhenColetaEncomendaDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(coletaEncomendaGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(ColetaEncomendaNotFoundException.class, () -> useCase.execute(id));
        verify(coletaEncomendaGateway, times(1)).findById(id);
    }

}