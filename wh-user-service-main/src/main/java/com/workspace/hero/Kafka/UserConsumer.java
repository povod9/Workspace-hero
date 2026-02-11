package com.workspace.hero.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    private final Logger log = LoggerFactory.getLogger(UserConsumer.class);

    @KafkaListener(topics = "bookings", groupId = "hero-group")
    public void listen(String message){
        log.info("User service caught the message");
    }
}
