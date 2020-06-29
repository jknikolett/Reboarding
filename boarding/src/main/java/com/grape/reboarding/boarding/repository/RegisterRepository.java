package com.grape.reboarding.boarding.repository;

import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    List<Register> findByRegisterDateAndStatus(LocalDate registerDate, RegisterStatus registerStatus);

    Optional<Register> findByUserIdAndRegisterDate(String userId, LocalDate registerDate);

    Register findByUserIdAndRegisterDateAndStatus(String userId, LocalDate registerDate, RegisterStatus registerStatus);

    Register findByUserIdAndRegisterDateAndStatusIn(String userId, LocalDate registerDate, List<RegisterStatus> registerStatus);

    List<Register> findByRegisterDateAndXAndYAndStatusInOrderByPosition(LocalDate registerDate, int x, int y, List<RegisterStatus> registerStatuses);

    List<Register> findByRegisterDateAndXGreaterThanAndYGreaterThanAndStatusInOrderByPosition(LocalDate registerDate, int x, int y, List<RegisterStatus> registerStatuses);
}
