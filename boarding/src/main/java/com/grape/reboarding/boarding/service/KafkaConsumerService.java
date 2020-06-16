package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class KafkaConsumerService {

    @Autowired
    private RegisterRepository registerRepository;

    @KafkaListener(topics = "${spring.kafka.topic.entry}")
    public void receivedAcceptedStatus(String userId) throws IOException {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), RegisterStatus.QUEUED);
        if (register != null){
            register.setStatus(RegisterStatus.ACCEPTED);
            registerRepository.save(register);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.exit}")
    public void receivedFinishedStatus(String userId) throws IOException {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), RegisterStatus.ACCEPTED);
        if (register != null){
            register.setStatus(RegisterStatus.FINISHED);
            registerRepository.save(register);
        }
    }

}
