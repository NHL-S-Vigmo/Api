package com.nhlstenden.student.vigmo.controllers.advice;

import com.nhlstenden.student.vigmo.exception.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorResponse handleDataException(DataNotFoundException exception) {
        return new ErrorResponse("Reading data failed: " + exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdProvidedInCreateRequestException.class)
    public ErrorResponse handleIdProvidedInCreateRequestException(IdProvidedInCreateRequestException exception) {
        return new ErrorResponse(String.format("'%s' To modify the object with id '%d' perform a PUT request.", exception.getMessage(), exception.getId()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public UsernameErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return new UsernameErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException exception) {
        return new ErrorResponse(String.format("Invalid credentials: %s", exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    public ErrorResponse handleDisabledException(DisabledException exception) {
        return new ErrorResponse(String.format("%s", exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenActionException.class)
    public ErrorResponse handleForbiddenActionException(ForbiddenActionException exception) {
        return new ErrorResponse(String.format("%s", exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    public ErrorResponse handleHttpMediaTypeNotAcceptableException(HttpServletRequest request) {
        return new ErrorResponse(request.getContentType() +
                " is not an acceptable MIME type. Acceptable MIME types are:" + MediaType.APPLICATION_JSON_VALUE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleAnyRuntimeException(RuntimeException exception) {
        return new ErrorResponse("Something unexpected went wrong: " + exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityIdRequirementNotMetException.class)
    public ErrorResponse handleEntityIdRequirementNotMetException(EntityIdRequirementNotMetException exception) {
        return new ErrorResponse("Error getting the id of an object: " + exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DeletionOfLastAdminAccountException.class)
    public ErrorResponse handleDeletionOfLastAdminAccountException(DeletionOfLastAdminAccountException exception) {
        return new ErrorResponse(String.format("%s", exception.getMessage()));
    }

    @Generated
    @AllArgsConstructor
    @Getter
    public static class ErrorResponse {
        private String error;
    }

    @Generated
    @AllArgsConstructor
    @Getter
    public static class UsernameErrorResponse {
        private String username;
    }
}
