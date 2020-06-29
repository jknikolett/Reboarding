package com.grape.reboarding.boarding.service;

import com.google.gson.reflect.TypeToken;
import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.dto.ChairStatus;
import com.grape.reboarding.boarding.dto.Desk;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceTest extends TestBase {

    @Mock
    RegisterRepository registerRepository;

    @Mock
    GsonService gsonService;

    @InjectMocks
    OfficeService officeService;

    private static final Resource office = new ClassPathResource("static/office.json");
    private static final Type listType = new TypeToken<List<Desk>>(){}.getType();

    @Test
    public void get_empty_chair_test() throws IOException {
        List<Desk> desks = new ArrayList<>();
        Mockito.when(gsonService.getOffice())
        .thenReturn(desks);
        Chair c = officeService.getEmptyChair(date);
        Assert.notNull(c, "Chair created");
    }

    private List<Desk> getOffice() {
        List<Desk> desks = new ArrayList<>();
        List<Chair> chairs1 = new ArrayList<>();
        chairs1.add(
                Chair.builder().position(new Point(23,15)).build()
        );
        chairs1.add(
                Chair.builder().position(new Point(28,15)).build()
        );
        chairs1.add(
                Chair.builder().position(new Point(32,15)).build()
        );
        Desk desk = aDesk(1, new ArrayList(), chairs1);
        desks.add(desk);
        return desks;
    }

    @Test
    public void chair_is_bookable() {
        List<Desk> office = getOffice();
        Mockito.when(gsonService.getOffice())
                .thenReturn(office);
        Chair c = officeService.getEmptyChair(date);
        Assertions.assertEquals(c.getStatus(), ChairStatus.BOOKABLE);
    }

    @Test
    public void get_layout_with_an_occupied_chair() {
        List<Register> queued = new ArrayList<>();
        queued.add(Register.builder().x(28).y(25).build());
        List<Register> accepted = new ArrayList<>();
        accepted.add(Register.builder().x(32).y(15).build());
        List<Desk> office = getOffice();
        officeService.setLevel(1);
        Mockito.when(gsonService.getOffice())
                .thenReturn(office);
        Mockito.when((registerRepository.findByRegisterDateAndStatus(date, RegisterStatus.QUEUED)))
                .thenReturn(queued);
        Mockito.when((registerRepository.findByRegisterDateAndStatus(date, RegisterStatus.ACCEPTED)))
                .thenReturn(accepted);
        List<Desk> layout = officeService.getLayout(date);
        Assert.notNull(layout, "Layout is not null");
        Assertions.assertEquals(ChairStatus.UN_BOOKABLE, layout.get(0).getChairs().get(0).getStatus());
        Assertions.assertEquals(ChairStatus.UN_BOOKABLE, layout.get(0).getChairs().get(1).getStatus());
        Assertions.assertEquals(ChairStatus.OCCUPIED, layout.get(0).getChairs().get(2).getStatus());
    }




}
