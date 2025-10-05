//package com.labequiped.productservice.service.kafka;
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducerService {
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//    public static final String TOPIC = "product-service-topic";
//
//    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void publish(String key, Object payload) {
//        kafkaTemplate.send(TOPIC, key, payload);
//    }
//}
