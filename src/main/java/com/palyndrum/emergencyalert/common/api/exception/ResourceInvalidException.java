package com.palyndrum.emergencyalert.common.api.exception;

public class ResourceInvalidException extends Exception {

    private final String msg;

    public ResourceInvalidException(String msg) {
        super(msg);

        this.msg = msg;
    }

    public static ResourceInvalidException create(String msg) {
        return new ResourceInvalidException(msg);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
