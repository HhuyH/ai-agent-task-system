package com.huy.aiagentsystem.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You do not have permission");
    }
}
