package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteMoradorUseCaseTest {

    private MoradorGateway moradorGateway;
    private DeleteMoradorUseCase useCase;

    @BeforeEach
    void setUp() {
        moradorGateway = mock(MoradorGateway.class);
        useCase = new DeleteMoradorUseCase(moradorGateway);
    }

    @Test
    @DisplayName("Deve deletar morador existente")
    void shouldDeleteExistingMorador() throws MoradorNotFoundException {
        UUID id = UUID.randomUUID();
        Morador morador = mock(Morador.class);

        when(moradorGateway.findById(id)).thenReturn(Optional.of(morador));

        useCase.execute(id);

        verify(moradorGateway, times(1)).findById(id);
        verify(moradorGateway, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar MoradorNotFoundException quando morador não existe")
    void shouldThrowMoradorNotFoundExceptionWhenMoradorDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(moradorGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(MoradorNotFoundException.class, () -> useCase.execute(id));

        verify(moradorGateway, times(1)).findById(id);
        verify(moradorGateway, never()).deleteById(id);
    }

}