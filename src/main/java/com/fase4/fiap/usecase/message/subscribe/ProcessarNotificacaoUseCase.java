package com.fase4.fiap.usecase.message.subscribe;

import com.fase4.fiap.entity.message.email.gateway.EmailGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class ProcessarNotificacaoUseCase {

    private final EmailGateway emailGateway;
    private final MoradorGateway moradorGateway;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    public ProcessarNotificacaoUseCase(EmailGateway emailGateway, MoradorGateway moradorGateway) {
        this.emailGateway = emailGateway;
        this.moradorGateway = moradorGateway;
    }

    @Transactional(readOnly = true)
    public void execute(Notificacao notificacao) {
        UUID apartamentoId = notificacao.getApartamentoId();
        UUID notificacaoId = notificacao.getId();

        List<Morador> moradores = moradorGateway.findMoradorByApartamentoId(apartamentoId);

        if (moradores.isEmpty()) {
            System.out.println("Nenhum morador encontrado para apartamento: " + apartamentoId);
            return;
        }

        String assunto = "Encomenda recebida em " +
                notificacao.getDataEnvio().format(DATE_FORMATTER);

        for (Morador morador : moradores) {
            String email = morador.getEmail();
            if (email.isBlank()) {
                continue;
            }

            try {
                emailGateway.send(
                        email,
                        assunto,
                        notificacao.getMensagem(),
                        notificacaoId,
                        morador.getId()
                );
            } catch (Exception e) {
                System.err.println("Falha ao enviar e-mail para: " + email + " | Erro: " + e.getMessage());
            }
        }
    }
}