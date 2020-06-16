package com.grape.reboarding.boarding.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "position")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_sequence")
    @SequenceGenerator(name = "position_sequence")
    private Long id;

    @Column(name = "position_date")
    private LocalDate positionDate;
    @Column(name = "actual_position")
    private Integer actualPosition;

    public void incrementPosition(){
        ++actualPosition;
    }
}
