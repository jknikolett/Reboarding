package com.grape.reboarding.terminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.topic.entry}")
    private String entryTopic;

    @Value("${spring.kafka.topic.exit}")
    private String exitTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendAcceptedStatus(String userId) {
        kafkaTemplate.send(entryTopic, userId);
    }

    public void sendFinishedStatus(String userId) {
        kafkaTemplate.send(exitTopic, userId);
    }

}
