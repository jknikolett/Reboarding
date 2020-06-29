package com.grape.reboarding.visualization.controller;

import com.grape.reboarding.visualization.dto.Desk;
import com.grape.reboarding.visualization.dto.RegisterDTO;
import com.grape.reboarding.visualization.service.GsonService;
import com.grape.reboarding.visualization.service.RestService;
import com.grape.reboarding.visualization.service.VisualizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RefreshScope
@RestController
@Slf4j
@Validated
public class VisualizationController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private RestService restService;

    @Autowired
    private GsonService gsonService;

    @Autowired
    private VisualizationService visualizationService;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping(path="/reservation/{base64String}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> reservation(@PathVariable("base64String") @NotNull String base64String, HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        RegisterDTO registerDTO = gsonService.fromJson(new String(Base64.getDecoder().decode(base64String)), RegisterDTO.class);
        Point position = restService.getChairForUser(registerDTO);
        return new ResponseEntity<>(visualizationService.createWorkplaceImage(position, localeResolver.resolveLocale(request)), headers, HttpStatus.OK);
    }

    @GetMapping(path="/layout", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> layout(HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        List<Desk> desks = restService.getOfficeLayout();
        return new ResponseEntity<>(visualizationService.createLayoutImage(desks, localeResolver.resolveLocale(request)), headers, HttpStatus.OK);
    }
}
