package com.grape.reboarding.terminal.controller;

import com.grape.reboarding.terminal.dto.EntryDTO;
import com.grape.reboarding.terminal.dto.ExitDTO;
import com.grape.reboarding.terminal.dto.Status;
import com.grape.reboarding.terminal.service.KafkaProducerService;
import com.grape.reboarding.terminal.service.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/terminal")
@Slf4j
public class TerminalController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RestService restService;

    @GetMapping("/entry/{userId}")
    public EntryDTO entry(@PathVariable("userId") String userId){
        if(restService.checkStatus(userId)){
            kafkaProducerService.sendAcceptedStatus(userId);
            return EntryDTO.builder().entry(Status.ACCEPTED).build();
        }
        return EntryDTO.builder().entry(Status.DENIED).build();
    }

    @GetMapping("/exit/{userId}")
    public ExitDTO exit(@PathVariable("userId") String userId){
        kafkaProducerService.sendFinishedStatus(userId);
        return ExitDTO.builder().exit(Status.SUCCEEDED).build();
    }
}
