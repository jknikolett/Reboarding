package com.grape.reboarding.boarding.dto;

import com.grape.reboarding.boarding.validation.RegisterConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RegisterConstraint
public class RegisterDTO {
    @NotBlank
    private String userId;
    @FutureOrPresent
    private LocalDate registerDate;
}
