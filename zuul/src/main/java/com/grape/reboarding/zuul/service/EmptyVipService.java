package com.grape.reboarding.zuul.service;

import org.springframework.stereotype.Service;

@Service
public class EmptyVipService implements VipService{
    @Override
    public void handleRequest() {
        //do nothing
    }
}
