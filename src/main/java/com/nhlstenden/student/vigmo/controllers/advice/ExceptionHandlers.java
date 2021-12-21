package com.nhlstenden.student.vigmo.controllers.advice;

import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.GenericTypeTransformerException;
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

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String message) {
            this.error = message;
        }

        public String getError() {
            return this.error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
