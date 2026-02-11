
//                          Kafka (soon)



//package com.workspace.hero.booking_service.Kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BookingProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final Logger log = LoggerFactory.getLogger(BookingProducer.class);
//
//    public BookingProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessage(String message){
//        kafkaTemplate.send("bookings", message);
//        log.info("Message was sent into kafka" + message);
//    }
//}
