package com.grape.reboarding.boarding.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = StatusUserIdValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusUserIdConstraint {
    String message() default "User not registered!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
