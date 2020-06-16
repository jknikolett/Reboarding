package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.CapacityRepository;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest extends TestBase {

    @Mock
    RegisterRepository registerRepository;

    @Mock
    CapacityRepository capacityRepository;

    @InjectMocks
    PositionService positionService;

    @Test
    public void get_position_for_a_userId() throws RegisterException{
        int expectedUserPosition = 5;
        Register register = aRegistration();
        setupRepositoryMock(registerRepository, userId, RegisterStatus.QUEUED, register);
        Mockito.when(capacityRepository.findByActiveTrue()).thenReturn(aCapacity());
        Mockito.when(registerRepository
                .countByStatusAndRegisterDate(RegisterStatus.FINISHED, register.getRegisterDate()))
                .thenReturn(5);
        Integer position = positionService.getPosition(userId);
        Assertions.assertEquals(expectedUserPosition, position);
    }

    @Test(expected = RegisterException.class)
    public void get_position_but_user_not_found()  throws RegisterException{
        positionService.getPosition(userId);
    }

}
