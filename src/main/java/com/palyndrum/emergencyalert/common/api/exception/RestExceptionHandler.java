package com.palyndrum.emergencyalert.common.api.exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.palyndrum.emergencyalert.common.api.payload.response.CommonRs;
import com.palyndrum.emergencyalert.common.constant.APIMessageConstant;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<CommonRs> handleException(Exception ex) {
        if (ex instanceof DataIntegrityException || ex instanceof ResourceInvalidException
                || ex instanceof InvalidFormatException || ex instanceof MissingServletRequestParameterException || ex instanceof PropertyReferenceException) {
            CommonRs commonRs = new CommonRs(APIMessageConstant.BAD_REQUEST);
            commonRs.addError(ex.getMessage());
            return new ResponseEntity<>(commonRs, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof HttpMessageNotReadableException) {
            CommonRs commonRs = new CommonRs(APIMessageConstant.BAD_REQUEST);
            commonRs.addError(APIMessageConstant.BAD_REQUEST);
            return new ResponseEntity<>(commonRs, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof ResourceConflictException) {
            CommonRs commonRs = new CommonRs(APIMessageConstant.CONFLICT);
            commonRs.addError(ex.getMessage());
            return new ResponseEntity<>(commonRs, HttpStatus.CONFLICT);
        } else if (ex instanceof ResourceNotFoundException) {
            CommonRs commonRs = new CommonRs(APIMessageConstant.NOT_FOUND);
            commonRs.addError(ex.getMessage());
            return new ResponseEntity<>(commonRs, HttpStatus.NOT_FOUND);
        } else if (ex instanceof BadCredentialsException) {
            CommonRs commonRs = new CommonRs(APIMessageConstant.BAD_REQUEST);
            commonRs.addError("Invalid username or password.");
            return new ResponseEntity<>(commonRs, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof AccessDeniedException) {
            CommonRs commonRs = new CommonRs(HttpStatus.FORBIDDEN.getReasonPhrase());
            commonRs.addError(ex.getMessage());
            return new ResponseEntity<>(commonRs, HttpStatus.FORBIDDEN);
        } else if (ex instanceof ResourceForbiddenException) {
            CommonRs commonRs = new CommonRs(HttpStatus.FORBIDDEN.getReasonPhrase());
            commonRs.addError(ex.getMessage());
            return new ResponseEntity<>(commonRs, HttpStatus.FORBIDDEN);
        } else {
            CommonRs commonRs = new CommonRs(APIMessageConstant.SYSTEM_ERROR);
            commonRs.addError(APIMessageConstant.SYSTEM_ERROR);
            commonRs.addError(ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(commonRs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}