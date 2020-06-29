package com.grape.reboarding.boarding.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grape.reboarding.boarding.dto.Desk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Slf4j
public class GsonService {

    private static final Resource office = new ClassPathResource("static/office.json");
    private static final Type listType = new TypeToken<List<Desk>>(){}.getType();

    @Autowired
    private Gson gson;

    public List<Desk> getOffice() {
        try {
            return gson.fromJson(new InputStreamReader(office.getInputStream()), listType);
        } catch (IOException e) {
            log.error("Office layout missing!", e);
            throw new RuntimeException("Office layout missing!", e);
        }
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
