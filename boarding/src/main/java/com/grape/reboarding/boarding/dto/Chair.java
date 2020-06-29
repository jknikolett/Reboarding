package com.grape.reboarding.boarding.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.awt.*;

@Data
@ToString
@Builder
public class Chair {

    public static final Integer CHAIR_RADIUS = 4;

    ChairStatus status;

    Point position;

    public boolean isInPosition(Point position){
        return this.position.equals(position);
    }

    public boolean isReservedOrOccupied(){
        return status == ChairStatus.RESERVED || status == ChairStatus.OCCUPIED;
    }

    public boolean isBookable(){
        return status == ChairStatus.BOOKABLE;
    }

    public void setReservation(){
        status = ChairStatus.RESERVED;
    }

    public boolean isInRange(Chair other, int range){
        int x1 = this.position.x, x2 = other.position.x;
        int y1 = this.position.y, y2 = other.position.y;
        int r1 = range + CHAIR_RADIUS, r2 = CHAIR_RADIUS;

        int distSq = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
        int radSumSq = (r1 + r2) * (r1 + r2);

        return distSq <= radSumSq;
    }
}
