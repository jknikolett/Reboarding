package com.grape.reboarding.zuul.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;

@Service
public class GsonService {

    @Autowired
    private Gson gson;

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(Reader reader, Class<T> classOfT) {
        return gson.fromJson(reader, classOfT);
    }
}
