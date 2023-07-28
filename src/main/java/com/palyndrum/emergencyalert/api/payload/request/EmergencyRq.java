package com.palyndrum.emergencyalert.api.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyRq {

    @NotNull(message = "latitude can't be empty.")
    private String latitude;

    @NotNull(message = "longitude can't be empty.")
    private String longitude;
}
