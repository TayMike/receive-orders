package com.fase4.fiap.usecase.recebimentoEncomenda;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.model.RecebimentoEncomenda;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.recebimentoEncomenda.dto.IRecebimentoEncomendaRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateRecebimentoEncomendaUseCaseTest {

    private RecebimentoEncomendaGateway recebimentoEncomendaGateway;
    private ApartamentoGateway apartamentoGateway;
    private PublicarNotificacaoUseCase publicarNotificacaoUseCase;
    private CreateRecebimentoEncomendaUseCase useCase;

    private UUID apartamentoId;
    private IRecebimentoEncomendaRegistrationData dados;

    @BeforeEach
    void setUp() {
        recebimentoEncomendaGateway = mock(RecebimentoEncomendaGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        publicarNotificacaoUseCase = mock(PublicarNotificacaoUseCase.class);
        useCase = new CreateRecebimentoEncomendaUseCase(recebimentoEncomendaGateway, apartamentoGateway, publicarNotificacaoUseCase);

        apartamentoId = UUID.randomUUID();
        dados = mock(IRecebimentoEncomendaRegistrationData.class);

        when(dados.apartamentoId()).thenReturn(apartamentoId);
        when(dados.descricao()).thenReturn("Pacote de livros");
        when(dados.dataEntrega()).thenReturn(OffsetDateTime.now());
        when(dados.estadoColeta()).thenReturn(RecebimentoEncomenda.EstadoColeta.PENDENTE);
    }

    @Test
    @DisplayName("Deve criar recebimento de encomenda e publicar notificação com dados válidos")
    void shouldCreateRecebimentoAndPublishNotificationWhenValidData() throws ApartamentoNotFoundException {
        Apartamento apartamento = mock(Apartamento.class);
        RecebimentoEncomenda savedRecebimento = new RecebimentoEncomenda(
                apartamentoId,
                dados.descricao(),
                dados.dataEntrega(),
                dados.estadoColeta()
        );

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamento));
        when(recebimentoEncomendaGateway.save(any(RecebimentoEncomenda.class))).thenReturn(savedRecebimento);

        RecebimentoEncomenda resultado = useCase.execute(dados);

        assertNotNull(resultado);
        assertEquals(apartamentoId, resultado.getApartamentoId());
        assertEquals(dados.descricao(), resultado.getDescricao());
        assertEquals(dados.dataEntrega(), resultado.getDataEntrega());
        assertEquals(dados.estadoColeta(), resultado.getEstadoColeta());

        verify(publicarNotificacaoUseCase).publish(argThat(notificacao ->
                notificacao.getApartamentoId().equals(apartamentoId)
        ));

        verify(apartamentoGateway).findById(apartamentoId);
        verify(recebimentoEncomendaGateway).save(any(RecebimentoEncomenda.class));
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldThrowApartamentoNotFoundExceptionWhenApartamentoNotFound() {
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        ApartamentoNotFoundException exception = assertThrows(
                ApartamentoNotFoundException.class,
                () -> useCase.execute(dados)
        );

        assertEquals("Apartamento not found: " + apartamentoId, exception.getMessage());

        verify(apartamentoGateway).findById(apartamentoId);
        verify(recebimentoEncomendaGateway, never()).save(any());
        verify(publicarNotificacaoUseCase, never()).publish(any());
    }

    @Test
    @DisplayName("Não deve publicar notificação se a descrição for nula (lança exceção)")
    void shouldNotPublishNotificationWhenDescricaoIsNull() {
        when(dados.descricao()).thenReturn(null);
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(mock(Apartamento.class)));

        assertThrows(NullPointerException.class, () -> useCase.execute(dados));

        verify(recebimentoEncomendaGateway, never()).save(any());
        verify(publicarNotificacaoUseCase, never()).publish(any());
    }

    @Test
    @DisplayName("Não deve salvar com estado PENDENTE se dataEntrega for futura")
    void shouldNotSaveWithPendenteWhenDataEntregaIsFuture() throws ApartamentoNotFoundException {
        OffsetDateTime future = OffsetDateTime.now().plusDays(3);
        when(dados.dataEntrega()).thenReturn(future);
        when(dados.estadoColeta()).thenReturn(RecebimentoEncomenda.EstadoColeta.PENDENTE);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dados));

        verify(apartamentoGateway, never()).findById(any());
        verify(recebimentoEncomendaGateway, never()).save(any());
    }

}