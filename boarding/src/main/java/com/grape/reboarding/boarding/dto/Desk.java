package com.grape.reboarding.boarding.dto;

import lombok.Data;
import lombok.ToString;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

@Data
@ToString
public class Desk {
    int id;
    boolean available;
    List<Chair> chairs;
    List<Integer> relatedDesks;

    Rectangle rectangle;

    public void markReservation(Point position){
        chairs.stream()
                .filter(c -> c.isInPosition(position))
                .findFirst()
                .ifPresent(Chair::setReservation);
    }

    public Stream<Chair> getReservedChairs(){
        return chairs.stream().filter(Chair::isReservedOrOccupied);
    }
}
