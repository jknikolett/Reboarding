package com.grape.reboarding.zuul.filters;

import com.grape.reboarding.zuul.dto.RegisterDTO;
import com.grape.reboarding.zuul.dto.ResponseDTO;
import com.grape.reboarding.zuul.service.GsonService;
import com.grape.reboarding.zuul.service.ServiceEndpoint;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.grape.reboarding.zuul.filters.VipFilter.VIP_FILTER_ORDER;
import static java.util.Optional.ofNullable;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_ENTITY_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

@Slf4j
@Component
public class RegisterDateFilter extends ZuulFilter {

    public static final int REGISTER_DATE_FILTER_ORDER = VIP_FILTER_ORDER + 1;

    @Value("${service.boarding.serviceId}")
    private String boardingServiceId;

    @Autowired
    private GsonService gsonService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return REGISTER_DATE_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(SERVICE_ID_KEY);
        String requestUri = (String) ctx.get(REQUEST_URI_KEY);
        return boardingServiceId.equals(serviceId) && requestUri.startsWith(ServiceEndpoint.REGISTER.getEndPoint());
    }

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        InputStream in = getInputStream();
        if (in != null){
            RegisterDTO registerDTO = gsonService.fromJson(new InputStreamReader(in), RegisterDTO.class);
            if(registerDTO.getRegisterDate() == null){
                registerDTO.setRegisterDate(LocalDate.now());

                String body = gsonService.toJson(registerDTO);
                ctx.setRequest(modifyRequest(ctx.getRequest(), body));
            }
        }
        return null;
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

    private HttpServletRequestWrapper modifyRequest(HttpServletRequest request, String body) {

        return new HttpServletRequestWrapper(request) {
            @Override
            public byte[] getContentData() {
                return body.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public int getContentLength() {
                return body.getBytes().length;
            }

            @Override
            public long getContentLengthLong() {
                return body.getBytes().length;
            }

            @Override
            public BufferedReader getReader() {
                return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8))));
            }

            @Override
            public ServletInputStream getInputStream() {
                return new ServletInputStreamWrapper(body.getBytes(StandardCharsets.UTF_8));
            }
        };
    }
}