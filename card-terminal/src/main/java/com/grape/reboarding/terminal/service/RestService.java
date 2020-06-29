package com.grape.reboarding.terminal.service;

import com.grape.reboarding.terminal.dto.ResponseDTO;
import com.grape.reboarding.terminal.dto.ResponseStatus;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EurekaClient eurekaClient;

    @Value("${service.boarding.serviceId}")
    private String boardingServiceId;

    public Boolean checkStatus(String userId){
        Application application = eurekaClient.getApplication(boardingServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/status/" + userId;
        ResponseEntity<ResponseDTO> response = restTemplate.getForEntity(url, ResponseDTO.class);
        return response.getStatusCode().is2xxSuccessful() && ResponseStatus.OK.equals(response.getBody().getStatus());
    }
}
