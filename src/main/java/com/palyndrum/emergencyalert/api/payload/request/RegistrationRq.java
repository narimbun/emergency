package com.palyndrum.emergencyalert.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRq {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "phone is mandatory")
    private String phone;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "confirmPassword is mandatory")
    private String confirmPassword;


}
