package com.nhlstenden.student.vigmo.exception;

public class EntityIdRequirementNotMetException extends RuntimeException {
    public EntityIdRequirementNotMetException(){
        super("Entity requires an id field in the dto");
    }
}
