package com.grape.reboarding.boarding.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "register")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "register_sequence")
    @SequenceGenerator(name = "register_sequence")
    private Long id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "register_date")
    private LocalDate registerDate;
    @Enumerated(EnumType.STRING)
    private RegisterStatus status;
    private Integer position;
}
