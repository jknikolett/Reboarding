package com.grape.reboarding.zuul.service;

import com.netflix.zuul.context.RequestContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

@Slf4j
@Getter
public abstract class AbstractVipService {

    @Autowired
    private GsonService gsonService;

    @Value("${reboarding.vip}")
    List<String> vips;

    protected void handleRequest(String endpoint, Object responseEntity){
        handleVip(getRequestUri().substring(endpoint.length()), responseEntity);
    }

    protected void handleVip(String userId, Object responseEntity){
        if(getVips().contains(userId)){
            log.info("VIP found {}!", userId);
            setResponse(responseEntity);
        }
    }

    protected String getRequestUri(){
        return (String) RequestContext.getCurrentContext().get(REQUEST_URI_KEY);
    }

    protected void setResponse(Object responseEntity){
        RequestContext ctx = RequestContext.getCurrentContext();
        // blocks the request
        ctx.setSendZuulResponse(false);

        // response to client
        ctx.setResponseBody(getGsonService().toJson(responseEntity));
        ctx.getResponse().setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        ctx.setResponseStatusCode(HttpStatus.OK.value());
    }

}
