package com.grape.reboarding.boarding.validation;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();

        List<String> errorList = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
            errorList.add(fieldError.getObjectName()+"."+fieldError.getField()+" : " +fieldError.getDefaultMessage() +" : rejected value [" +fieldError.getRejectedValue() +"]" );
        });
        result.getGlobalErrors().forEach((fieldError) -> {
            errorList.add(fieldError.getObjectName()+" : " +fieldError.getDefaultMessage() );
        });

        return new Error(HttpStatus.BAD_REQUEST, ex.getMessage(), errorList);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Set<ConstraintViolation<?>> result = ex.getConstraintViolations();

        List<String> errorList = new ArrayList<>();
        result.forEach((constraintViolation) -> {
            errorList.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });

        return new Error(HttpStatus.BAD_REQUEST, ex.getMessage(), errorList);
    }

    @Data
    public static class Error{
        private int errorCode;
        private String error;
        private String errorMessage;
        private List<String> fieldErrors = new ArrayList<>();

        public Error(HttpStatus status, String message, List<String> fieldErrors ) {
            this.errorCode = status.value();
            this.error = status.name();
            this.errorMessage = message;
            this.fieldErrors = fieldErrors;
        }
    }
}
