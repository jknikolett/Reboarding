package com.grape.reboarding.boarding.base;

import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.dto.ChairStatus;
import com.grape.reboarding.boarding.dto.Desk;
import com.grape.reboarding.boarding.entity.Position;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.apache.tomcat.jni.Local;
import org.mockito.Mockito;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TestBase {

    protected String userId = "000-3332";
    protected LocalDate date = LocalDate.now();
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
                .findByUserIdAndRegisterDateAndStatus(userId, date, registerStatus))
                .thenReturn(register);
    }

    protected void setupRepositoryMockIn(RegisterRepository registerRepository,
                                       String userId,
                                       List<RegisterStatus> statues,
                                       Register register) {
        if (register == null) { register = new Register(); }
        Mockito.when(registerRepository
                .findByUserIdAndRegisterDateAndStatusIn(userId, LocalDate.now(), statues))
                .thenReturn(register);
    }

    protected String sampleJSONContent() {
        return "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"available\": true,\n" +
                "    \"relatedDesks\": [\n" +
                "      2,\n" +
                "      8\n" +
                "    ],\n" +
                "    \"chairs\": [\n" +
                "      {\n" +
                "        \"position\": {\n" +
                "          \"x\": 737,\n" +
                "          \"y\": 96\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"position\": {\n" +
                "          \"x\": 756,\n" +
                "          \"y\": 96\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "  ]";
    }

    protected  Position aPosition(LocalDate date) {
        Position position = new Position();
        position.setId(1L);
        position.setActualPosition(2);
        position.setPositionDate(date);
        return position;
    }

    protected Desk aDesk(Integer deskId, List<Integer> relatedDesks, List<Chair> chairs) {
        Desk desk = new Desk();
        desk.setAvailable(true);

        desk.setChairs(chairs);
        desk.setId(deskId);
        desk.setRelatedDesks(relatedDesks);

        return desk;
    }
}
