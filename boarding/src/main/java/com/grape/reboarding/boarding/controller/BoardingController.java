package com.grape.reboarding.boarding.controller;

import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.dto.ResponseDTO;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.service.GsonService;
import com.grape.reboarding.boarding.service.PositionService;
import com.grape.reboarding.boarding.service.RegisterService;
import com.grape.reboarding.boarding.validation.StatusUserIdConstraint;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Base64;

@RefreshScope
@RestController
@Validated
@Slf4j
public class BoardingController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private GsonService gsonService;

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${service.zuul.serviceId}")
    private String zuulServiceId;

    @GetMapping(value = "/status/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO status(@PathVariable("userId") @StatusUserIdConstraint @NotBlank String userId){
        return ResponseDTO.builder().position(positionService.getPosition(userId)).build();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO register(@Valid @RequestBody RegisterDTO registerDTO){
        Register register = registerService.register(registerDTO);
        Integer position = positionService.getPosition(register);
        return ResponseDTO.builder().position(position).imageUrl(createImageUrl(register)).build();
    }

    private String createImageUrl(Register register){
        RegisterDTO registerDTO = RegisterDTO.builder().userId(register.getUserId()).registerDate(register.getRegisterDate()).build();
        String base64String = Base64.getEncoder().encodeToString(gsonService.toJson(registerDTO).getBytes());
        Application application = eurekaClient.getApplication(zuulServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        return  "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/reservation/" + base64String;
    }

}
