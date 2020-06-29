package com.grape.reboarding.zuul.filters;

import com.grape.reboarding.zuul.service.VipServiceFactory;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.grape.reboarding.zuul.filters.PathFilter.PATH_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

@Component
@Slf4j
public class VipFilter extends ZuulFilter {

    public static final int VIP_FILTER_ORDER = PATH_FILTER_ORDER + 1;

    @Autowired
    VipServiceFactory vipServiceFactory;

    @Value("${service.zuul.serviceIds}")
    List<String> services;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return VIP_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(SERVICE_ID_KEY);
        return services.contains(serviceId);
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(SERVICE_ID_KEY);
        String requestUri = (String) ctx.get(REQUEST_URI_KEY);
        vipServiceFactory.getVipService(serviceId, getPath(requestUri.replaceAll("/+", "/"))).handleRequest();
        return null;
    }

    private String getPath(String requestUri){
        int indexOfDash = requestUri.indexOf("/", 1);
        int endIndex = indexOfDash == -1 ? requestUri.length() : indexOfDash;
        return requestUri.substring(0, endIndex);
    }
}