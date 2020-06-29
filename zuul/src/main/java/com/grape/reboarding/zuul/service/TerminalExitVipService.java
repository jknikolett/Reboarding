package com.grape.reboarding.zuul.service;

import com.grape.reboarding.zuul.dto.ExitDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public class TerminalExitVipService extends AbstractVipService implements VipService{

    @Override
    public void handleRequest() {
        handleRequest(ServiceEndpoint.EXIT.getEndPoint(), ExitDTO.SUCCEEDED);
    }
}
