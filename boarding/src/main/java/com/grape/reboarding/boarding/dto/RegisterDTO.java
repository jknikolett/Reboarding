package com.grape.reboarding.boarding.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegisterDTO {
    private String userId;
    private LocalDate registerDate;
}
