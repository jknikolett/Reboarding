package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.PositionRepository;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionService positionService;

    public Register register(RegisterDTO registerDTO) throws RegisterException {
        validate(registerDTO);
        LocalDate registerDate = registerDTO.getRegisterDate() != null ? registerDTO.getRegisterDate() : LocalDate.now();
        if(isAlreadyRegistered(registerDTO.getUserId(), registerDate)){
            throw new RegisterException("User " + registerDTO.getUserId() + " already registered!");
        }
        Register register = Register.builder()
                .userId(registerDTO.getUserId())
                .registerDate(registerDate)
                .position(getNextPosition(registerDate))
                .status(RegisterStatus.QUEUED)
                .build();
        return registerRepository.save(register);
    }

    private Integer getNextPosition(LocalDate registerDate) {
        Position position = positionRepository.findByPositionDate(registerDate);
        if(position == null){
            position = positionService.createPosition(registerDate);
        }
        position.incrementPosition();
        positionRepository.save(position);
        return position.getActualPosition();
    }

    private boolean isAlreadyRegistered(String userId, LocalDate registerDate){
        Register queued = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, registerDate, RegisterStatus.QUEUED);
        Register accepted = registerRepository.findByUserIdAndRegisterDateAndStatus(userId, registerDate, RegisterStatus.ACCEPTED);
        return queued != null || accepted != null;
    }

    private void validate(RegisterDTO registerDTO) throws RegisterException {
        if(StringUtils.isBlank(registerDTO.getUserId())){
            throw new RegisterException("UserId can not be empty!");
        }
    }
}
