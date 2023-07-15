package com.palyndrum.emergencyalert.common.api.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DataIntegrityException extends DataIntegrityViolationException {

    private final String msg;

    public DataIntegrityException(String msg) {
        super(msg);

        this.msg = msg;
    }

    public static DataIntegrityException create(String msg) {
        return new DataIntegrityException(msg);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
