package com.palyndrum.emergencyalert.common.api.exception;

public class InvalidJWTException extends Exception {

    private final String msg;

    public InvalidJWTException(String msg) {
        super(msg);

        this.msg = msg;
    }

    public static InvalidJWTException create(String msg) {
        return new InvalidJWTException(msg);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
