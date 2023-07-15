package com.palyndrum.emergencyalert.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRq {

    @NotBlank(message = "Username can't be empty.")
    private String username;

    @NotBlank(message = "Password can't be empty.")
    private String password;
}
