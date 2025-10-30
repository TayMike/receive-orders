package com.fase4.fiap.infraestructure.message;

import com.fase4.fiap.entity.message.notificacaoLeitura.model.NotificacaoLeitura;
import com.fase4.fiap.usecase.message.NotificarLeituraUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoLeituraController {

    private final NotificarLeituraUseCase notificarLeituraUseCase;

    public NotificacaoLeituraController(NotificarLeituraUseCase notificarLeituraUseCase) {
        this.notificarLeituraUseCase = notificarLeituraUseCase;
    }

    @GetMapping("/{notificacaoId}/lido/{moradorId}")
    public ResponseEntity<byte[]> marcarComoLido(
            @PathVariable UUID notificacaoId,
            @PathVariable UUID moradorId,
            HttpServletRequest request) {

        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        NotificacaoLeitura notificacaoLeitura = new NotificacaoLeitura(notificacaoId, moradorId, ip, userAgent);
        notificarLeituraUseCase.execute(notificacaoLeitura);

        // Retorna pixel 1x1 transparente
        byte[] pixel = new byte[]{
                (byte)0x47, (byte)0x49, (byte)0x46, (byte)0x38, (byte)0x39, (byte)0x61,
                (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x80, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x21, (byte)0xf9, (byte)0x04, (byte)0x01, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x2c, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00,
                (byte)0x00, (byte)0x02, (byte)0x02, (byte)0x44, (byte)0x01, (byte)0x00,
                (byte)0x3b
        };

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .body(pixel);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) ip = request.getRemoteAddr();
        return ip;
    }
}