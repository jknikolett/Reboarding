package com.grape.reboarding.visualization.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.awt.*;

@Data
@ToString
@Builder
public class Chair {
    ChairStatus status;
    Point position;
}
