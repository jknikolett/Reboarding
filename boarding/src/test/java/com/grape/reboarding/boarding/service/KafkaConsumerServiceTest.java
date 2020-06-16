package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.base.TestBase;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class KafkaConsumerServiceTest extends TestBase {

    @Mock
    RegisterRepository registerRepository;

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
        kafkaConsumerService.receivedFinishedStatus(userId);
        verifyRegisterRepositorySaveMethod(1);
    }

    @Test
    public void when_userId_not_found_in_accepted_state_handling() throws IOException {
        kafkaConsumerService.receivedFinishedStatus(userId);
        verifyRegisterRepositorySaveMethod(0);
    }



    private void verifyRegisterRepositorySaveMethod(int count) {
        Mockito.verify(registerRepository, Mockito.times(count))
                .save(Mockito.any());
    }


}
