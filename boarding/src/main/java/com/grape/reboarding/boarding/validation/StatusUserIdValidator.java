package com.grape.reboarding.boarding.validation;

import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
public class StatusUserIdValidator implements ConstraintValidator<StatusUserIdConstraint, String> {

    @Autowired
    RegisterRepository registerRepository;

    @Override
    public void initialize(StatusUserIdConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext constraintValidatorContext) {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatusIn(userId, LocalDate.now(), Arrays.asList(RegisterStatus.QUEUED, RegisterStatus.ACCEPTED));
        return register != null;
    }
}
