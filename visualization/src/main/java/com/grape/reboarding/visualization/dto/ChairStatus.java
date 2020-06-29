package com.grape.reboarding.visualization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public enum ChairStatus {
    BOOKABLE(Color.GREEN, "chair.bookable"),
    RESERVED(Color.YELLOW, "chair.reserved"),
    OCCUPIED(Color.RED, "chair.occupied"),
    UN_BOOKABLE(Color.GRAY, "chair.un.bookable");

    private final Color color;
    private final String legend;
}
