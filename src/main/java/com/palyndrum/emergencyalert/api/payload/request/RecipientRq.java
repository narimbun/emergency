package com.palyndrum.emergencyalert.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipientRq {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Phone is mandatory")
    private String phone;
}
