package com.grape.reboarding.boarding.repository;

import com.grape.reboarding.boarding.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findByPositionDate(LocalDate positionDate);
}
