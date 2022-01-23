package com.nhlstenden.student.vigmo.exception;

public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(){
        super("You're not allowed in here.");
    }
}
