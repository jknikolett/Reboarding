package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.CapacityRepository;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PositionService {

    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private CapacityRepository capacityRepository;

    public Position createPosition(LocalDate registerDate) {
        return Position.builder()
                .actualPosition(0)
                .positionDate(registerDate)
                .build();
    }

    public Integer getPosition(String userId) throws RegisterException {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), RegisterStatus.QUEUED);
        if(register == null){
            register = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), RegisterStatus.ACCEPTED);
        }
        if(register == null){
            throw new RegisterException("User " + userId + " was not registered in the system!");
        }
        return getPosition(register);
    }

    public Integer getPosition(Register register) {
        Integer position = register.getPosition();
        Integer capacity = capacityRepository.findByActiveTrue().getMaximum();
        Integer finishedCount = registerRepository.countByStatusAndRegisterDate(RegisterStatus.FINISHED, register.getRegisterDate());
        return Math.max(position - capacity - finishedCount, 0);
    }
}
