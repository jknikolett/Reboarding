package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PositionService {

    @Autowired
    private RegisterRepository registerRepository;

    public Position createPosition(LocalDate registerDate) {
        return Position.builder()
                .actualPosition(0)
                .positionDate(registerDate)
                .build();
    }

    public Integer getPosition(String userId) {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatusIn(userId, LocalDate.now(), Arrays.asList(RegisterStatus.QUEUED, RegisterStatus.ACCEPTED));
        return getPosition(register);
    }

    public Integer getPosition(Register register) {
        if(register.isPositionEmpty()){
            List<Register> registers = registerRepository.findByRegisterDateAndXAndYAndStatusInOrderByPosition(register.getRegisterDate(), 0, 0, Arrays.asList(RegisterStatus.QUEUED));
            return findIndex(registers, register);
        } else {
            return 0;
        }
    }

    private Integer findIndex(List<Register> registers, Register item){
        for(int i = 0; i < registers.size(); i++){
            if(item.getUserId().equals(registers.get(i).getUserId())){
                return i + 1;
            }
        }
        return -1;
    }
}
