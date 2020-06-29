package com.grape.reboarding.zuul.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {

    public static final ResponseDTO OK = ResponseDTO.builder().status(ResponseStatus.OK).build();

    private ResponseStatus status;
}
