package com.fase4.fiap.usecase.message.publish;

import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublicarNotificacaoUseCaseTest {

    @Mock
    private NotificacaoGateway notificacaoGateway;

    @InjectMocks
    private PublicarNotificacaoUseCase useCase;

    @Test
    @DisplayName("Deve chamar o gateway para publicar a notificação")
    void shouldCallGatewayToPublishNotification() {
        Notificacao notificacao = mock(Notificacao.class);

        useCase.publish(notificacao);

        verify(notificacaoGateway, times(1)).publish(notificacao);
        verifyNoMoreInteractions(notificacaoGateway);
    }

    @Test
    @DisplayName("Deve propagar exceção se o gateway lançar RuntimeException")
    void shouldPropagateExceptionWhenGatewayThrows() {
        // Given
        Notificacao notificacao = mock(Notificacao.class);
        RuntimeException expectedException = new RuntimeException("Falha ao publicar");

        doThrow(expectedException)
                .when(notificacaoGateway)
                .publish(notificacao);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> useCase.publish(notificacao)
        );

        org.junit.jupiter.api.Assertions.assertEquals(expectedException, thrown);
        verify(notificacaoGateway).publish(notificacao);
    }

    @Test
    @DisplayName("Não deve interagir com o gateway se a notificação for null")
    void shouldNotInteractWithGatewayWhenNotificationIsNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.publish(null));
        verifyNoInteractions(notificacaoGateway);
    }

}