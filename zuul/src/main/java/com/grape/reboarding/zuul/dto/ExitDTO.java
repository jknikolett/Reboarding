package com.grape.reboarding.zuul.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExitDTO {

    public static final ExitDTO SUCCEEDED = ExitDTO.builder().exit(Status.SUCCEEDED).build();

    private Status exit;
}
