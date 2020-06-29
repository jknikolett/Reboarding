package com.grape.reboarding.visualization.dto;

import lombok.Data;
import lombok.ToString;

import java.awt.*;
import java.util.List;

@Data
@ToString
public class Desk {
    int id;
    boolean available;
    List<Chair> chairs;
    List<Integer> relatedDesks;
    Rectangle rectangle;
}
