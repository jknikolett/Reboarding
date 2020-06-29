package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@Setter
public class KafkaConsumerService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${reboarding.position.kafka}")
    private Integer notificationId;

    @KafkaListener(topics = "${spring.kafka.topic.entry}")
    public void receivedAcceptedStatus(String userId) {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), RegisterStatus.QUEUED);
        if (register != null){
            register.setStatus(RegisterStatus.ACCEPTED);
            registerRepository.save(register);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.exit}")
    public void receivedFinishedStatus(String userId) {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), RegisterStatus.ACCEPTED);
        if (register != null){
            register.setStatus(RegisterStatus.FINISHED);
            registerRepository.save(register);
            setChairForNextPerson(register.getRegisterDate());
            notifyPerson(register.getRegisterDate());
        }
    }

    private void setChairForNextPerson(LocalDate registerDate) {
        List<Register> queued = registerRepository.findByRegisterDateAndXAndYAndStatusInOrderByPosition(registerDate, 0,0, Arrays.asList(RegisterStatus.QUEUED));
        if(queued.size() > 0){
            Register next = queued.get(0);
            Chair chair = officeService.getEmptyChair(registerDate);
            next.setX(chair.getPosition().x);
            next.setY(chair.getPosition().y);
            registerRepository.save(next);
        }
    }

    private void notifyPerson(LocalDate registerDate){
        List<Register> queued = registerRepository.findByRegisterDateAndXAndYAndStatusInOrderByPosition(registerDate, 0,0, Arrays.asList(RegisterStatus.QUEUED));
        if(queued.size() >= notificationId){
            kafkaProducerService.sendNotification(queued.get(notificationId - 1).getUserId());
        }
    }

}
