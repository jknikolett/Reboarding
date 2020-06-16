package com.grape.reboarding.boarding.repository;

import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    Register findByUserIdAndRegisterDateAndStatus(String userId, LocalDate registerDate, RegisterStatus registerStatus);

    Integer countByStatusAndRegisterDate(RegisterStatus registerStatus, LocalDate registerDate);
}
