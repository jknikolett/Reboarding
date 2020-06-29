package com.grape.reboarding.boarding.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RegisterValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterConstraint {
    String message() default "User already registered!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
