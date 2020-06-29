package com.grape.reboarding.visualization.service;

import com.grape.reboarding.visualization.dto.Desk;
import com.grape.reboarding.visualization.dto.RegisterDTO;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${service.boarding.serviceId}")
    private String boardingServiceId;

    public Point getChairForUser(RegisterDTO registerDTO){
        Application application = eurekaClient.getApplication(boardingServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/visualization/chair/" + registerDTO.getUserId() + "/" + registerDTO.getRegisterDate().format(DateTimeFormatter.ISO_DATE);
        return restTemplate.getForObject(url, Point.class);
    }

    public List<Desk> getOfficeLayout(){
        Application application = eurekaClient.getApplication(boardingServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/visualization/layout/" + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Desk>>(){}).getBody();
    }
}
