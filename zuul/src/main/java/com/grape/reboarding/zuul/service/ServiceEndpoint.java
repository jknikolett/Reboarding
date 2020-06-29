package com.grape.reboarding.zuul.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ServiceEndpoint {

    REGISTER("/register"),
    STATUS("/status"),
    ENTRY("/entry"),
    EXIT("/exit");

    private final String endPoint;

    public static ServiceEndpoint getByEndPoint(String endpoint){
        return Arrays.stream(ServiceEndpoint.values())
                .filter(se->se.getEndPoint().equals(endpoint))
                .findFirst()
                .orElse(null);
    }
}
