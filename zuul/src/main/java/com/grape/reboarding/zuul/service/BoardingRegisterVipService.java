package com.grape.reboarding.zuul.service;

import com.grape.reboarding.zuul.dto.RegisterDTO;
import com.grape.reboarding.zuul.dto.ResponseDTO;
import com.netflix.zuul.context.RequestContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.Optional.ofNullable;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_ENTITY_KEY;

@Service
@Slf4j
@Getter
public class BoardingRegisterVipService extends AbstractVipService implements VipService{

    @Override
    public void handleRequest() {
        InputStream in = getInputStream();
        if (in != null){
            RegisterDTO registerDTO = getGsonService().fromJson(new InputStreamReader(in), RegisterDTO.class);
            handleVip(registerDTO.getUserId(), ResponseDTO.OK);
        }
    }

    private InputStream getInputStream(){
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            return ofNullable((InputStream) ctx.get(REQUEST_ENTITY_KEY)).orElse(ctx.getRequest().getInputStream());
        } catch (IOException e) {
            log.error("Couldn't open request input stream!", e);
        }
        return null;
    }

}
