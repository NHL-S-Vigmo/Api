package com.nhlstenden.student.vigmo.exception;

public class DataAlreadyExistsException extends RuntimeException {
    private final long id;
    public DataAlreadyExistsException(String message, long id){
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
