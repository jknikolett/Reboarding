package com.grape.reboarding.zuul.dto;

import com.google.gson.annotations.JsonAdapter;
import com.grape.reboarding.zuul.service.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String userId;
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate registerDate;
}
