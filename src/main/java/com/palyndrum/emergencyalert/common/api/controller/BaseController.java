package com.palyndrum.emergencyalert.common.api.controller;

import com.palyndrum.emergencyalert.common.api.payload.response.CommonRs;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonRs handleValidationExceptions(MethodArgumentNotValidException ex) {
        CommonRs commonRs = new CommonRs("failed");
        ex.getBindingResult().getAllErrors().forEach(error -> commonRs.addError(String.format("%s:%s", ((FieldError) error).getField(), error.getDefaultMessage())));
        return commonRs;
    }

}
