package com.grape.reboarding.zuul.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntryDTO {

    public static final EntryDTO ACCEPTED = EntryDTO.builder().entry(Status.ACCEPTED).build();

    private Status entry;
}
