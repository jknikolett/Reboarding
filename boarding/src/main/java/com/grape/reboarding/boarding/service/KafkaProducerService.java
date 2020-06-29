package com.grape.reboarding.boarding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.topic.notification}")
    private String notificationTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String userId) {
        kafkaTemplate.send(notificationTopic, userId);
    }

}
