package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetMoradorUseCaseTest {

    private MoradorGateway moradorGateway;
    private GetMoradorUseCase useCase;

    @BeforeEach
    void setUp() {
        moradorGateway = mock(MoradorGateway.class);
        useCase = new GetMoradorUseCase(moradorGateway);
    }

    @Test
    @DisplayName("Deve retornar morador quando encontrado pelo id")
    void shouldReturnMoradorWhenFoundById() throws MoradorNotFoundException {
        UUID id = UUID.randomUUID();
        Morador morador = mock(Morador.class);

        when(moradorGateway.findById(id)).thenReturn(Optional.of(morador));

        Morador resultado = useCase.execute(id);

        assertNotNull(resultado);
        assertEquals(morador, resultado);
        verify(moradorGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar MoradorNotFoundException quando morador não existe")
    void shouldThrowMoradorNotFoundExceptionWhenMoradorDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(moradorGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(MoradorNotFoundException.class, () -> useCase.execute(id));
        verify(moradorGateway, times(1)).findById(id);
    }

}