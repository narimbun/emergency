package com.palyndrum.emergencyalert.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationRq {

    @NotBlank(message = "OTP can't be empty.")
    private String verificationCode;
}
