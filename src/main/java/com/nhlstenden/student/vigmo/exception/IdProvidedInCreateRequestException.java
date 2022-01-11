package com.nhlstenden.student.vigmo.exception;

public class IdProvidedInCreateRequestException extends RuntimeException {
    private final long id;
    public IdProvidedInCreateRequestException(String message, long id){
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
