package com.palyndrum.emergencyalert.common.api.exception;

public class ResourceForbiddenException extends Exception {

    private final String msg;

    public ResourceForbiddenException(String msg) {
        super(msg);

        this.msg = msg;
    }

    public static ResourceForbiddenException create(String msg) {
        return new ResourceForbiddenException(msg);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
