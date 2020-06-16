package com.grape.reboarding.boarding.controller;

import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.dto.ResponseDTO;
import com.grape.reboarding.boarding.dto.ResponseStatus;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.service.PositionService;
import com.grape.reboarding.boarding.service.RegisterException;
import com.grape.reboarding.boarding.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/boarding")
public class BoardingController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private PositionService positionService;

    @GetMapping(value = "/status/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO status(@PathVariable("userId") String userId){
        try {
            return ResponseDTO.builder().position(positionService.getPosition(userId)).build();
        } catch (RegisterException e) {
            return ResponseDTO.builder().status(ResponseStatus.ERROR).message(e.getMessage()).build();
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO register(@RequestBody RegisterDTO registerDTO){
        try {
            Register register = registerService.register(registerDTO);
            Integer position = positionService.getPosition(register);
            return ResponseDTO.builder().position(position).build();
        } catch (RegisterException e) {
            return ResponseDTO.builder().status(ResponseStatus.ERROR).message(e.getMessage()).build();
        }
    }

}
