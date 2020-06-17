package com.dung.phan.product.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditProduceMessage {
    private static final String TOPIC = "audit-service-topic";
    @Autowired
    private Producer producer;
    public void sendKafkaMessage(String payload) {
        log.info("Sending Kafka message: " + payload);
        producer.sendMessage(payload,TOPIC);
    }

}
