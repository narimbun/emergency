package com.palyndrum.emergencyalert.common.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonRs extends ErrorRs {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object body;

    public CommonRs(String message) {
        this.message = message;
    }

    public void addError(String error) {
        if (this.getErrors() == null)
            this.setErrors(new ArrayList<>());

        this.getErrors().add(error);
    }
}
