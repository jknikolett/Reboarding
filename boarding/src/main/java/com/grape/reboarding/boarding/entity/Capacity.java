package com.grape.reboarding.boarding.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "capacity")
@Data
@NoArgsConstructor
public class Capacity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "capacity_sequence")
    @SequenceGenerator(name = "capacity_sequence")
    private Long id;

    private Integer percent;
    private Integer maximum;
    private Boolean active;
}
