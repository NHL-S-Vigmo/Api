package com.nhlstenden.student.vigmo.controllers.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String originName ;
            //Check if the error originates from a field or an object
            if(error instanceof FieldError){
                originName = ((FieldError) error).getField();
            }else{
                originName = error.getObjectName();
            }

            String message = error.getDefaultMessage();
            errors.put(originName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
