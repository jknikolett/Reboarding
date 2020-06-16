package com.grape.reboarding.terminal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExitDTO {
    private Status exit;
}
