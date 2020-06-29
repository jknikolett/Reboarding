package com.grape.reboarding.boarding.controller;

import com.grape.reboarding.boarding.dto.Desk;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import com.grape.reboarding.boarding.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RefreshScope
@RestController
@RequestMapping("/visualization")
@Validated
public class VisualizationController {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private OfficeService officeService;

    @GetMapping(value = "/chair/{userId}/{registerDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Point chair(@PathVariable("userId") @NotBlank String userId, @PathVariable("registerDate") @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registerDate){
        return registerRepository.findByUserIdAndRegisterDate(userId, registerDate)
                .orElse(Register.builder().x(0).y(0).build()).getChairPosition();
    }

    @GetMapping(value = "/layout/{registerDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Desk> layout(@PathVariable("registerDate") @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registerDate) throws IOException {
        return officeService.getLayout(registerDate);
    }
}
