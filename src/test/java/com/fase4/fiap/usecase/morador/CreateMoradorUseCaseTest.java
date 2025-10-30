package com.fase4.fiap.usecase.morador;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.morador.dto.IMoradorRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class CreateMoradorUseCaseTest {

    private MoradorGateway moradorGateway;
    private ApartamentoGateway apartamentoGateway;
    private CreateMoradorUseCase useCase;

    @BeforeEach
    void setUp() {
        moradorGateway = mock(MoradorGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new CreateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve criar morador com dados válidos")
    void shouldCreateMoradorWithValidData() throws ApartamentoNotFoundException {
        IMoradorRegistrationData dados = mock(IMoradorRegistrationData.class);
        Apartamento apartamento = mock(Apartamento.class);
        Morador morador = mock(Morador.class);

        UUID apartamentoId = UUID.randomUUID();

        when(dados.apartamentoId()).thenReturn(List.of(apartamentoId));
        when(dados.cpf()).thenReturn("12345678901");
        when(dados.nome()).thenReturn("Teste Nome");
        when(dados.telefone()).thenReturn(List.of("11999999999"));
        when(dados.email()).thenReturn("teste@hotmail.com");

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamento));
        when(moradorGateway.save(any(Morador.class))).thenReturn(morador);

        Morador resultado = useCase.execute(dados);

        assertNotNull(resultado);
        verify(apartamentoGateway, times(1)).findById(apartamentoId);
        verify(moradorGateway, times(1)).save(any(Morador.class));
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldThrowApartamentoNotFoundExceptionWhenApartamentoDoesNotExist() {
        IMoradorRegistrationData dados = mock(IMoradorRegistrationData.class);
        UUID apartamentoId = UUID.randomUUID();

        when(dados.apartamentoId()).thenReturn(List.of(apartamentoId));
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(dados));
        verify(apartamentoGateway, times(1)).findById(apartamentoId);
        verify(moradorGateway, never()).save(any());
    }

}