package com.grape.reboarding.boarding.repository;

import com.grape.reboarding.boarding.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByPositionDate(LocalDate positionDate);
}
