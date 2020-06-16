package com.grape.reboarding.boarding.base;

import com.grape.reboarding.boarding.entity.Capacity;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.mockito.Mockito;
import java.time.LocalDate;


public class TestBase {

    protected String userId = "000-3332";

    protected Capacity aCapacity() {
        Capacity capacity = new Capacity();
        capacity.setActive(true);
        capacity.setId(1L);
        capacity.setMaximum(10);
        capacity.setPercent(10);
        return capacity;
    }

    protected Register aRegistration() {
        Register register = new Register();
        register.setStatus(RegisterStatus.QUEUED);
        register.setId(1L);
        register.setPosition(20);
        register.setRegisterDate(LocalDate.now());
        register.setUserId(userId);
        return register;
    }

    protected void setupRepositoryMock(RegisterRepository registerRepository,
                                       String userId,
                                       RegisterStatus registerStatus,
                                       Register register) {
        if (register == null) { register = new Register(); }
        Mockito.when(registerRepository
                .findByUserIdAndRegisterDateAndStatus(userId, LocalDate.now(), registerStatus))
                .thenReturn(register);
    }
}
