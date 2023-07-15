package com.palyndrum.emergencyalert.common.api.exception;

public class ResourceNotFoundException extends Exception {

    private final String msg;

    public ResourceNotFoundException(String msg) {
        super(msg);

        this.msg = msg;
    }

    public static ResourceNotFoundException create(String msg) {
        return new ResourceNotFoundException(msg);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
