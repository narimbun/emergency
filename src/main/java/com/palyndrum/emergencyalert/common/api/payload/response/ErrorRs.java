package com.palyndrum.emergencyalert.common.api.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
class ErrorRs {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

}
