package com.grape.reboarding.boarding.validation;

import com.grape.reboarding.boarding.dto.RegisterDTO;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Slf4j
public class RegisterValidator implements ConstraintValidator<RegisterConstraint, RegisterDTO> {

    @Autowired
    RegisterRepository registerRepository;

    @Override
    public void initialize(RegisterConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(RegisterDTO registerDTO, ConstraintValidatorContext constraintValidatorContext) {
        Register register = registerRepository.findByUserIdAndRegisterDateAndStatusIn(registerDTO.getUserId(), registerDTO.getRegisterDate(), Arrays.asList(RegisterStatus.QUEUED, RegisterStatus.ACCEPTED));
        return register == null;
    }
}
