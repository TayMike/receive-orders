package com.fase4.fiap.usecase.coletaEncomenda;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.entity.recebimentoEncomenda.exception.RecebimentoEncomendaNotFoundException;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateColetaEncomendaUseCaseTest {

    private ColetaEncomendaGateway coletaEncomendaGateway;
    private RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private CreateColetaEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        coletaEncomendaGateway = mock(ColetaEncomendaGateway.class);
        recebimentoEncomendaGateway = mock(RecebimentoEncomendaGateway.class);
        useCase = new CreateColetaEncomendaUseCase(coletaEncomendaGateway, recebimentoEncomendaGateway);
    }

    @Test
    @DisplayName("Deve criar coletaEncomenda com dados válidos")
    void shouldCreateColetaEncomendaWithValidData() throws RecebimentoEncomendaNotFoundException {
        IColetaEncomendaRegistrationData dados = mock(IColetaEncomendaRegistrationData.class);
        RecebimentoEncomenda recebimentoEncomenda = mock(RecebimentoEncomenda.class);
        ColetaEncomenda coletaEncomenda = mock(ColetaEncomenda.class);

        UUID recebimentoEncomendaId = UUID.randomUUID();

        when(dados.recebimentoEncomendaId()).thenReturn(recebimentoEncomendaId);
        when(dados.cpfMoradorColeta()).thenReturn("12345678912");
        when(dados.nomeMoradorColeta()).thenReturn("Teste Nome");
        when(dados.dataColeta()).thenReturn(OffsetDateTime.now());

        when(recebimentoEncomendaGateway.findById(recebimentoEncomendaId)).thenReturn(Optional.of(recebimentoEncomenda));
        when(coletaEncomendaGateway.save(any(ColetaEncomenda.class))).thenReturn(coletaEncomenda);

        ColetaEncomenda resultado = useCase.execute(dados);

        assertNotNull(resultado);
        verify(recebimentoEncomendaGateway, times(1)).findById(recebimentoEncomendaId);
        verify(coletaEncomendaGateway, times(1)).save(any(ColetaEncomenda.class));
    }

    @Test
    @DisplayName("Deve lançar RecebimentoEncomendaNotFoundException quando recebimentoEncomenda não existe")
    void shouldThrowRecebimentoEncomendaNotFoundExceptionWhenRecebimentoEncomendaDoesNotExist() {
        IColetaEncomendaRegistrationData dados = mock(IColetaEncomendaRegistrationData.class);
        UUID recebimentoEncomendaId = UUID.randomUUID();

        when(dados.recebimentoEncomendaId()).thenReturn(recebimentoEncomendaId);
        when(recebimentoEncomendaGateway.findById(recebimentoEncomendaId)).thenReturn(Optional.empty());
        when(dados.dataColeta()).thenReturn(OffsetDateTime.now());

        assertThrows(RecebimentoEncomendaNotFoundException.class, () -> useCase.execute(dados));

        verify(recebimentoEncomendaGateway, times(1)).findById(recebimentoEncomendaId);
        verify(coletaEncomendaGateway, never()).save(any());
    }

}