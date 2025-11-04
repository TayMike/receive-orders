package com.fase4.fiap.usecase.message.subscribe;

import com.fase4.fiap.entity.message.email.gateway.EmailGateway;
import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessarNotificacaoUseCaseTest {

    @Mock private EmailGateway emailGateway;
    @Mock private MoradorGateway moradorGateway;
    @Mock private NotificacaoGateway notificacaoGateway;

    @InjectMocks private ProcessarNotificacaoUseCase useCase;

    private final UUID apartamentoId = UUID.randomUUID();
    private final UUID notificacaoId = UUID.randomUUID();
    private final OffsetDateTime dataEnvio = OffsetDateTime.now();

    private Notificacao createNotificacao() {
        Notificacao notificacao = mock(Notificacao.class);
        when(notificacao.getApartamentoId()).thenReturn(apartamentoId);
        when(notificacao.getId()).thenReturn(notificacaoId);
        when(notificacao.getDataEnvio()).thenReturn(dataEnvio);
        when(notificacao.getMensagem()).thenReturn("Sua encomenda chegou!");
        return notificacao;
    }

    @Test
    @DisplayName("Deve enviar e-mail e salvar notificação para cada morador com e-mail válido")
    void shouldSendEmailAndSaveNotificationForEachValidMorador() {
        Notificacao notificacao = createNotificacao();
        UUID morador1Id = UUID.randomUUID();
        UUID morador2Id = UUID.randomUUID();

        Morador morador1 = createMorador("joao@email.com");
        Morador morador2 = createMorador("maria@email.com");

        when(morador1.getId()).thenReturn(morador1Id);
        when(morador2.getId()).thenReturn(morador2Id);

        when(moradorGateway.findAllByApartamentoId(apartamentoId))
                .thenReturn(List.of(morador1, morador2));

        useCase.execute(notificacao);

        verify(emailGateway).send(
                eq("joao@email.com"),
                argThat(s -> s.contains("Encomenda recebida em")),
                eq("Sua encomenda chegou!"),
                eq(notificacaoId),
                eq(morador1Id)
        );

        verify(emailGateway).send(
                eq("maria@email.com"),
                argThat(s -> s.contains("Encomenda recebida em")),
                eq("Sua encomenda chegou!"),
                eq(notificacaoId),
                eq(morador2Id)
        );

        verify(notificacaoGateway, times(2)).save(notificacao);
    }

    @Test
    @DisplayName("Não deve enviar e-mail nem salvar se não houver moradores")
    void shouldDoNothingWhenNoMoradores() {
        Notificacao notificacao = mock(Notificacao.class);
        when(notificacao.getApartamentoId()).thenReturn(apartamentoId);

        when(moradorGateway.findAllByApartamentoId(apartamentoId))
                .thenReturn(Collections.emptyList());

        useCase.execute(notificacao);

        verifyNoInteractions(emailGateway);
        verifyNoInteractions(notificacaoGateway);
    }

    @Test
    @DisplayName("Deve ignorar morador com e-mail vazio ou em branco")
    void shouldSkipMoradorWithBlankEmail() {
        Notificacao notificacao = createNotificacao();
        UUID validId = UUID.randomUUID();

        Morador moradorSemEmail = createMorador("   ");
        Morador moradorComEmail = createMorador("valid@email.com");

        when(moradorComEmail.getId()).thenReturn(validId);

        when(moradorGateway.findAllByApartamentoId(apartamentoId))
                .thenReturn(List.of(moradorSemEmail, moradorComEmail));

        useCase.execute(notificacao);

        verify(emailGateway, never()).send(eq("   "), any(), any(), any(), any());

        verify(emailGateway).send(
                eq("valid@email.com"),
                any(),
                eq("Sua encomenda chegou!"),
                eq(notificacaoId),
                eq(validId)
        );

        verify(notificacaoGateway, times(1)).save(notificacao);
    }

    @Test
    @DisplayName("Deve continuar processando outros moradores se um envio falhar")
    void shouldContinueOnEmailFailure() {
        Notificacao notificacao = createNotificacao();
        Morador morador1 = createMorador("erro@email.com");
        Morador morador2 = createMorador("ok@email.com");

        when(moradorGateway.findAllByApartamentoId(apartamentoId))
                .thenReturn(List.of(morador1, morador2));

        doThrow(new RuntimeException("SMTP error"))
                .when(emailGateway)
                .send(eq("erro@email.com"), any(), any(), any(), any());

        useCase.execute(notificacao);

        verify(emailGateway).send(eq("erro@email.com"), any(), any(), any(), any());
        verify(emailGateway).send(eq("ok@email.com"), any(), any(), any(), any());
        verify(notificacaoGateway).save(notificacao); // salva apenas para o sucesso
    }

    @Test
    @DisplayName("Não deve salvar notificação se o envio de e-mail falhar")
    void shouldNotSaveNotificationOnEmailFailure() {
        Notificacao notificacao = createNotificacao();
        Morador morador = createMorador("fail@email.com");

        when(moradorGateway.findAllByApartamentoId(apartamentoId))
                .thenReturn(List.of(morador));

        doThrow(new RuntimeException("Falha"))
                .when(emailGateway)
                .send(any(), any(), any(), any(), any());

        useCase.execute(notificacao);

        verify(notificacaoGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve formatar assunto com data correta")
    void shouldFormatSubjectWithCorrectDate() {
        Notificacao notificacao = createNotificacao();
        Morador morador = createMorador("test@email.com");

        when(moradorGateway.findAllByApartamentoId(apartamentoId))
                .thenReturn(List.of(morador));

        useCase.execute(notificacao);

        String expectedPattern = "Encomenda recebida em \\d{2}/\\d{2}/\\d{4} às \\d{2}:\\d{2}";
        verify(emailGateway).send(
                any(),
                argThat(subject -> subject.matches(expectedPattern)),
                any(),
                any(),
                any()
        );
    }

    private Morador createMorador(String email) {
        Morador morador = mock(Morador.class);
        when(morador.getEmail()).thenReturn(email);
        return morador;
    }
}