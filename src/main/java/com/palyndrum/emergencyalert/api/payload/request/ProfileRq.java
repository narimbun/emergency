package com.palyndrum.emergencyalert.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRq {

    @NotBlank(message = "name is mandatory")
    private String name;



}
