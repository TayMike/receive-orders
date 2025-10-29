//package com.fase4.fiap.infraestructure.message.consumer;
//
//import com.fase4.fiap.entity.message.confirmacao.model.Confirmacao;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//import com.fase4.fiap.usecase.message.ProcessarConfirmacaoUseCase;
//
//@Component
//public class ConfirmacaoConsumer {
//
//    private final ProcessarConfirmacaoUseCase processarConfirmacaoUseCase;
//
//    public ConfirmacaoConsumer(ProcessarConfirmacaoUseCase processarConfirmacaoUseCase) {
//        this.processarConfirmacaoUseCase = processarConfirmacaoUseCase;
//    }
//
//    @KafkaListener(topics = "confirmacao-topic", groupId = "notificacao-group")
//    public void consumir(Confirmacao confirmacao) {
//        processarConfirmacaoUseCase.executar(confirmacao);
//    }
//
//}