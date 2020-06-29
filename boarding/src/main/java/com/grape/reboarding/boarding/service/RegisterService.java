package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.PositionRepository;
import com.grape.reboarding.boarding.repository.RegisterRepository;
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
    @Autowired
    private OfficeService officeService;

    public Register register(RegisterDTO registerDTO) {
        Chair chair = officeService.getEmptyChair(registerDTO.getRegisterDate());
        Register register = Register.builder()
                .userId(registerDTO.getUserId())
                .registerDate(registerDTO.getRegisterDate())
                .position(getNextPosition(registerDTO.getRegisterDate()))
                .status(RegisterStatus.QUEUED)
                .x(chair.getPosition().x)
                .y(chair.getPosition().y)
                .build();
        return registerRepository.save(register);
    }

    private Integer getNextPosition(LocalDate registerDate) {
        Position position = positionRepository.findByPositionDate(registerDate)
                .orElse(positionService.createPosition(registerDate));
        position.incrementPosition();
        positionRepository.save(position);
        return position.getActualPosition();
    }
}
