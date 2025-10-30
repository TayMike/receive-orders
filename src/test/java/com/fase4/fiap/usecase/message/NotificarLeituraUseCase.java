package com.fase4.fiap.usecase.message;

import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.gateway.NotificacaoLeituraGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificarLeituraUseCaseTest {

    @Mock private NotificacaoLeituraGateway notificacaoLeituraGateway;
    @Mock private NotificacaoGateway notificacaoGateway;

    @InjectMocks private NotificarLeituraUseCase useCase;

    private final UUID notificacaoId = UUID.randomUUID();

    @Test
    @DisplayName("Deve salvar leitura e marcar notificação como lida quando ela existe")
    void shouldSaveLeituraAndMarkAsReadWhenNotificationExists() {
        NotificacaoLeitura leitura = mock(NotificacaoLeitura.class);
        when(leitura.getNotificacaoId()).thenReturn(notificacaoId);

        var notificacao = mock(com.fase4.fiap.entity.message.notificacao.model.Notificacao.class);
        when(notificacaoGateway.findById(notificacaoId)).thenReturn(Optional.of(notificacao));

        useCase.execute(leitura);

        verify(notificacaoLeituraGateway).save(leitura);
        verify(notificacao).marcarComoLida();
        verify(notificacaoGateway).save(notificacao);
    }

    @Test
    @DisplayName("Deve salvar leitura mas NÃO alterar notificação se ela não existir")
    void shouldSaveLeituraButNotTouchNotificationWhenNotFound() {
        NotificacaoLeitura leitura = mock(NotificacaoLeitura.class);
        when(leitura.getNotificacaoId()).thenReturn(notificacaoId);

        when(notificacaoGateway.findById(notificacaoId)).thenReturn(Optional.empty());

        useCase.execute(leitura);

        verify(notificacaoLeituraGateway).save(leitura);
        verify(notificacaoGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção se notificacaoLeitura for null")
    void shouldThrowExceptionWhenLeituraIsNull() {

        IllegalArgumentException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(null)
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "NotificacaoLeitura não pode ser nula",
                thrown.getMessage()
        );

        verifyNoInteractions(notificacaoLeituraGateway);
        verifyNoInteractions(notificacaoGateway);
    }
}