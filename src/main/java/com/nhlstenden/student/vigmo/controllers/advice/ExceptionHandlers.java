package com.nhlstenden.student.vigmo.controllers.advice;

import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.GenericTypeTransformerException;
import com.nhlstenden.student.vigmo.exception.IdProvidedInCreateRequestException;
import com.nhlstenden.student.vigmo.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorResponse handleDataException(DataNotFoundException exception, HttpServletRequest request) {
        return new ErrorResponse("Reading data failed: " + exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdProvidedInCreateRequestException.class)
    public ErrorResponse handleIdProvidedInCreateRequestException(IdProvidedInCreateRequestException exception, HttpServletRequest request) {
        return new ErrorResponse(String.format("'%s' To modify the object with id '%d' perform a PUT request.", exception.getMessage(), exception.getId()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException exception, HttpServletRequest request) {
        return new ErrorResponse(String.format("Duplicate resource: %s", exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ErrorResponse handleHttpMediaTypeNotAcceptableException() {
        return new ErrorResponse("acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleAnyRuntimeException(RuntimeException exception) {
        return new ErrorResponse("Something unexpected went wrong: " + exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericTypeTransformerException.class)
    public ErrorResponse handleGenericTypeTransformerException(GenericTypeTransformerException exception) {
        return new ErrorResponse("Transforming of generic class went wrong: " + exception.getMessage());
    }

    @Generated
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ErrorResponse {
        private String error;
    }
}
