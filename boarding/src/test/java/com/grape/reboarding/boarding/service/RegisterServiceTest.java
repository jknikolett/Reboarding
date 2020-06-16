package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.PositionRepository;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest extends TestBase {

    @Mock
    RegisterRepository registerRepository;

    @Mock
    PositionRepository positionRepository;

    @Mock
    PositionService positionService;

    @InjectMocks
    RegisterService registerService;

    private RegisterDTO aRegisterDTO(LocalDate date) {
        RegisterDTO dto = new RegisterDTO();
        dto.setUserId(userId);
        dto.setRegisterDate(date);
        return dto;
    }

    private Position aPosition(LocalDate date) {
        Position position = new Position();
        position.setId(1L);
        position.setActualPosition(2);
        position.setPositionDate(date);
        return position;
    }

    @Test
    public void register_a_user()  throws RegisterException{
        LocalDate registerDate = LocalDate.now();
        Register register = aRegistration();
        register.setId(null);
        register.setPosition(3);
        RegisterDTO registerDTO = aRegisterDTO(registerDate);
        Position position = aPosition(registerDate);
        Mockito.when(positionRepository.findByPositionDate(registerDate))
                .thenReturn(position);
        Mockito.when(registerRepository.save(register)).thenReturn(register);
        registerService.register(registerDTO);
        Mockito.verify(registerRepository, Mockito.times(1))
                .save(register);
    }

    @Test
    public void register_a_user_no_position()  throws RegisterException{
        LocalDate registerDate = LocalDate.now();
        RegisterDTO registerDTO = aRegisterDTO(registerDate);
        Register register = aRegistration();
        register.setId(null);
        register.setPosition(3);
        Position position = aPosition(registerDate);
        Mockito.when(positionService.createPosition(registerDate))
                .thenReturn(position);
        Mockito.when(registerRepository.save(register)).thenReturn(register);
        registerService.register(registerDTO);
        Mockito.verify(registerRepository, Mockito.times(1))
                .save(register);
    }

    @Test(expected = RegisterException.class)
    public void register_blank_userId() throws RegisterException {
        LocalDate registerDate = LocalDate.now();
        RegisterDTO registerDTO = aRegisterDTO(registerDate);
        registerDTO.setUserId("");

        registerService.register(registerDTO);
    }

    @Test(expected = RegisterException.class)
    public void user_is_already_registered() throws RegisterException {
        LocalDate now = LocalDate.now();
        Mockito.when(registerRepository.findByUserIdAndRegisterDateAndStatus(userId, now, RegisterStatus.QUEUED))
                .thenReturn(new Register());
        registerService.register(aRegisterDTO(now));
    }

}
