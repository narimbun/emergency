package com.palyndrum.emergencyalert.common.api.exception;

public class ResourceConflictException extends Exception {

    private final String msg;

    public ResourceConflictException(String msg) {
        super(msg);

        this.msg = msg;
    }

    public static ResourceConflictException create(String msg) {
        return new ResourceConflictException(msg);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
