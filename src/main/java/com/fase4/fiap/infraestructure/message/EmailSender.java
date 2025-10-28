package com.fase4.fiap.infraestructure.message;

import com.fase4.fiap.entity.message.email.gateway.EmailGateway;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Component
public class EmailSender implements EmailGateway {

    private final Session session;
    private final String fromEmail;
    private final String baseUrl;

    public EmailSender(
            @Value("${spring.mail.host}") String host,
            @Value("${spring.mail.port}") int port,
            @Value("${spring.mail.username}") String username,
            @Value("${spring.mail.password}") String password,
            @Value("${spring.mail.from:no-reply@fiap.com}") String fromEmail,
            @Value("${app.url.base}") String baseUrl
    ) {
        this.fromEmail = fromEmail;
        this.baseUrl = normalizeUrl(baseUrl);

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        this.session = Session.getInstance(props, auth);
    }

    @PostConstruct
    public void validate() {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("app.url.base não configurado! Configure em application.yml");
        }
    }

    private String normalizeUrl(String url) {
        if (url == null) return null;
        return url.endsWith("/") ? url : url + "/";
    }

    @Override
    public void send(String to, String subject, String body) {
        send(to, subject, body, null, null);
    }

    @Override
    public void send(String to, String subject, String body, UUID notificacaoId, UUID moradorId) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

            String finalBody = body;
            if (notificacaoId != null && moradorId != null) {
                String pixel = String.format(
                        "<img src='%sapi/notificacoes/%s/lido/%s' width='1' height='1' style='display:none'/>",
                        baseUrl, notificacaoId, moradorId
                );
                finalBody = """
                    <html>
                    <body>
                        %s
                        <hr>
                        <small>Notificação automática do condomínio.</small>
                        %s
                    </body>
                    </html>
                    """.formatted(body, pixel);
                msg.setContent(finalBody, "text/html; charset=utf-8");
            } else {
                msg.setText(body);
            }

            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail para: " + to, e);
        }
    }
}