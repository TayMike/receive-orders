//package com.fase4.fiap.infraestructure.message.producer;
//
//import com.fase4.fiap.entity.message.confirmacao.model.Confirmacao;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConfirmacaoProducer {
//
//    private final KafkaTemplate<String, Confirmacao> kafkaTemplate;
//    private static final String TOPIC = "confirmacao-topic";
//
//    public ConfirmacaoProducer(KafkaTemplate<String, Confirmacao> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void enviar(Confirmacao confirmacao) {
//        kafkaTemplate.send(TOPIC, confirmacao);
//    }
//
//}