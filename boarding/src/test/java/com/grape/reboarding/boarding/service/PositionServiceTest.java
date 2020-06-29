package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest extends TestBase {

    @Mock
    RegisterRepository registerRepository;

    @InjectMocks
    PositionService positionService;

    private ArrayList<Register> getRegisters(int targetPosition) {
        ArrayList<Register> registers = new ArrayList<>();
        String userId = "";
        for(int i=1;i<=5;i++) {
            userId = i+"";
            if (i == targetPosition) {
                userId = this.userId;
            }
            registers.add(Register.builder().userId(userId).build());
        }
        return registers;
    }


    @Test
    public void get_position_for_a_userId() {
        int expectedUserPosition = 5;
        Register register = aRegistration();
        register.setX(0);
        register.setY(0);
        ArrayList<RegisterStatus> statuses  = new ArrayList<>();
        statuses.add(RegisterStatus.QUEUED);
        statuses.add(RegisterStatus.ACCEPTED);
        setupRepositoryMockIn(registerRepository, userId, statuses, register);
        Mockito.when(registerRepository
                .findByRegisterDateAndXAndYAndStatusInOrderByPosition(register.getRegisterDate(), 0, 0, Arrays.asList(RegisterStatus.QUEUED)))
                .thenReturn(getRegisters(5));
        Integer position = positionService.getPosition(userId);
        Assertions.assertEquals(expectedUserPosition, position);
    }

    @Test
    public void get_first_position_for_a_userId() {
        int expectedUserPosition = 0;
        Register register = aRegistration();
        register.setX(1);
        register.setY(1);
        ArrayList<RegisterStatus> statuses  = new ArrayList<>();
        statuses.add(RegisterStatus.QUEUED);
        statuses.add(RegisterStatus.ACCEPTED);
        setupRepositoryMockIn(registerRepository, userId, statuses, register);

        Integer position = positionService.getPosition(userId);
        Assertions.assertEquals(expectedUserPosition, position);
    }

    @Test
    public void index_not_found() {
        int expectedUserPosition = -1;
        Register register = aRegistration();
        register.setX(1);
        register.setY(0);
        ArrayList<RegisterStatus> statuses  = new ArrayList<>();
        statuses.add(RegisterStatus.QUEUED);
        statuses.add(RegisterStatus.ACCEPTED);
        setupRepositoryMockIn(registerRepository, userId, statuses, register);

        Integer position = positionService.getPosition(userId);
        Assertions.assertEquals(expectedUserPosition, position);
    }



}
