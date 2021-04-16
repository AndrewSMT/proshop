package com.andrew.proshop.exception;

public class EntityCouldNotBeFoundException extends RuntimeException {

    public EntityCouldNotBeFoundException(String message) {
        super(message);
    }
}
