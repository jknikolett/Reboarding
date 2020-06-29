package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.repository.PositionRepository;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest extends TestBase {

    @Mock
    OfficeService officeService;

    @Mock
    RegisterRepository registerRepository;

    @Mock
    PositionRepository positionRepository;

    @Mock
    PositionService positionService;

    @Mock
    Chair chair;

    @InjectMocks
    RegisterService registerService;

    private RegisterDTO aRegisterDTO(LocalDate date) {
        RegisterDTO dto = new RegisterDTO();
        dto.setUserId(userId);
        dto.setRegisterDate(date);
        return dto;
    }



    @Test
    public void register_a_user_with_empty_chair() {
        LocalDate registerDate = LocalDate.now();
        Register register = aRegistration();
        register.setId(null);
        register.setPosition(3);
        register.setX(1);
        register.setY(1);
        RegisterDTO registerDTO = aRegisterDTO(registerDate);
        Position position = aPosition(registerDate);
        Mockito.when(positionRepository.findByPositionDate(registerDate)).thenReturn(java.util.Optional.of(position));
        Mockito.when(registerRepository.save(register)).thenReturn(register);
        Mockito.when(chair.getPosition()).thenReturn(new Point(1,1));


        Mockito.when(officeService.getEmptyChair(registerDate)).thenReturn(chair);
        registerService.register(registerDTO);
        Mockito.verify(registerRepository, Mockito.times(1))
                .save(register);
    }

    @Test
    public void register_a_user_no_position() {
        LocalDate registerDate = LocalDate.now();
        RegisterDTO registerDTO = aRegisterDTO(registerDate);
        Register register = aRegistration();
        register.setId(null);
        register.setPosition(3);
        register.setX(1);
        register.setY(1);
        Position position = aPosition(registerDate);
        Mockito.when(positionService.createPosition(registerDate))
                .thenReturn(position);

        Mockito.when(chair.getPosition()).thenReturn(new Point(1,1));
        Mockito.when(officeService.getEmptyChair(registerDate)).thenReturn(chair);
        Mockito.when(registerRepository.save(register)).thenReturn(register);
        registerService.register(registerDTO);
        Mockito.verify(registerRepository, Mockito.times(1))
                .save(register);
    }



    @Test
    public void find_empty_chair_to_user() {

        Register register = aRegistration();
        register.setId(null);
        register.setPosition(3);
        register.setX(1);
        register.setY(1);

        Mockito.when(chair.getPosition()).thenReturn(new Point(1,1));
        Mockito.when(officeService.getEmptyChair(register.getRegisterDate())).thenReturn(chair);

        Mockito.when(positionRepository.findByPositionDate(date)
                .orElse(positionService.createPosition(date))).thenReturn(aPosition(date));

        RegisterDTO dto = aRegisterDTO(date);

        registerService.register(dto);

        Mockito.verify(registerRepository, Mockito.times(1))
                .save(register);
    }

}
