package com.grape.reboarding.zuul.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class VipServiceFactory {

    @Autowired
    BoardingRegisterVipService boardingRegisterVipService;

    @Autowired
    BoardingStatusVipService boardingStatusVipService;

    @Autowired
    TerminalEntryVipService terminalEntryVipService;

    @Autowired
    TerminalExitVipService terminalExitVipService;

    @Autowired
    EmptyVipService emptyVipService;

    @Value("${service.boarding.serviceId}")
    String boardingServiceId;

    @Value("${service.terminal.serviceId}")
    String terminalServiceId;

    private Map<String, VipService> services;

    @PostConstruct
    public void init() {
        services = new HashMap<>(ServiceEndpoint.values().length);
        services.put(boardingServiceId + ServiceEndpoint.REGISTER, boardingRegisterVipService);
        services.put(boardingServiceId + ServiceEndpoint.STATUS, boardingStatusVipService);
        services.put(terminalServiceId + ServiceEndpoint.ENTRY, terminalEntryVipService);
        services.put(terminalServiceId + ServiceEndpoint.EXIT, terminalExitVipService);
    }

    public VipService getVipService(String serviceId, String endPoint){
        ServiceEndpoint serviceEndpoint = ServiceEndpoint.getByEndPoint(endPoint);
        return services.getOrDefault(serviceId + serviceEndpoint, emptyVipService);
    }
}
