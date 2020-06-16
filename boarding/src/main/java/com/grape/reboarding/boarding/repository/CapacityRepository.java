package com.grape.reboarding.boarding.repository;

import com.grape.reboarding.boarding.entity.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {

    Capacity findByActiveTrue();
}
