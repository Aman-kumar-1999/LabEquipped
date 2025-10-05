//package com.labequiped.productservice.service.kafka;
//
//import com.labequiped.productservice.service.ProductService;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumerService {
//
//    private final ProductService productService;
//
//    public KafkaConsumerService(ProductService productService) {
//        this.productService = productService;
//    }
//
//    // Generic listener: payload must carry an "action" field or type to decide what to do
//    @KafkaListener(topics = KafkaProducerService.TOPIC, groupId = "product-service-group")
//    public void listen(Object payload) {
//        // (pseudo) inspect payload, then call productService to perform actual operation
//        // Example: if payload is Map with action=refreshCache, id=...
//        // It's left intentionally generic — implement your payload model (e.g. ProductEvent DTO)
//    }
//
//}
