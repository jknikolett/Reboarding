package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class KafkaConsumerServiceTest extends TestBase {

    @Mock
    OfficeService officeService;

    @Mock
    RegisterRepository registerRepository;

    @Mock
    Chair chair;

    @Mock
    KafkaProducerService kafkaProducerService;

    @InjectMocks
    KafkaConsumerService kafkaConsumerService;

    private String userId = "000-3332";

    @Test
    public void when_userId_consumed_and_reach_queued_status() throws IOException {
        setupRepositoryMock(registerRepository, userId, RegisterStatus.QUEUED, null);
        kafkaConsumerService.receivedAcceptedStatus(userId);
        verifyRegisterRepositorySaveMethod(1);
    }

    @Test
    public void when_userId_not_found_in_queued_state_handling() throws IOException {
        kafkaConsumerService.receivedAcceptedStatus(userId);
        verifyRegisterRepositorySaveMethod(0);
    }

    @Test
    public void when_userId_consumed_and_reach_accepted_status() throws IOException {
        setupRepositoryMock(registerRepository, userId, RegisterStatus.ACCEPTED, null);
        kafkaConsumerService.setNotificationId(1);
        kafkaConsumerService.receivedFinishedStatus(userId);
        verifyRegisterRepositorySaveMethod(1);
    }

    @Test
    public void when_userId_not_found_in_accepted_state_handling() throws IOException {
        kafkaConsumerService.receivedFinishedStatus(userId);
        verifyRegisterRepositorySaveMethod(0);
    }

    @Test
    public void when_userId_consumed_and_the_queue_list_size_above_0_and_user_notified() {
        LocalDate registerDate = LocalDate.now();
        List<Register> registers = new ArrayList<Register>();
        registers.add(aRegistration());
        registers.add(aRegistration());

        LocalDate now = date;
        setupRepositoryMock(registerRepository, userId, RegisterStatus.ACCEPTED, aRegistration());
        Mockito.when(registerRepository.findByRegisterDateAndXAndYAndStatusInOrderByPosition(registerDate, 0,0, Arrays.asList(RegisterStatus.QUEUED)))
                .thenReturn(registers);

        Mockito.when(chair.getPosition()).thenReturn(new Point(1,2));
        Mockito.when(officeService.getEmptyChair(registerDate)).thenReturn(chair);

        kafkaConsumerService.setNotificationId(1);
        kafkaConsumerService.receivedFinishedStatus(userId);
        verifyRegisterRepositorySaveMethod(2);
    }

    private void verifyRegisterRepositorySaveMethod(int count) {
        Mockito.verify(registerRepository, Mockito.times(count))
                .save(Mockito.any());
    }


}
