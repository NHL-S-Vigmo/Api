package com.nhlstenden.student.vigmo.exception;

public class DeletionOfLastAdminAccountException extends RuntimeException {
    public DeletionOfLastAdminAccountException() {
        super("Deleting the last admin account will leave the api unusable");
    }
}
