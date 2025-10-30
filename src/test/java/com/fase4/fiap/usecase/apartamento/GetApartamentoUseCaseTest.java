package com.fase4.fiap.usecase.apartamento;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetApartamentoUseCaseTest {

    private ApartamentoGateway apartamentoGateway;
    private GetApartamentoUseCase useCase;

    @BeforeEach
    void setUp() {
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new GetApartamentoUseCase(apartamentoGateway);
    }

    @Test
    @DisplayName("Deve retornar apartamento quando encontrado pelo id")
    void shouldReturnApartamentoWhenFoundById() throws ApartamentoNotFoundException {
        UUID id = UUID.randomUUID();
        Apartamento apartamento = mock(Apartamento.class);

        when(apartamentoGateway.findById(id)).thenReturn(Optional.of(apartamento));

        Apartamento resultado = useCase.execute(id);

        assertNotNull(resultado);
        assertEquals(apartamento, resultado);
        verify(apartamentoGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldThrowApartamentoNotFoundExceptionWhenApartamentoDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(apartamentoGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(id));
        verify(apartamentoGateway, times(1)).findById(id);
    }

}
