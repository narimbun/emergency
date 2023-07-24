package com.palyndrum.emergencyalert.service;

import com.palyndrum.emergencyalert.api.payload.request.RegistrationRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.entity.User;

public interface RegistrationService {

    User registration(RegistrationRq registrationRq) throws ResourceInvalidException, ResourceConflictException;
}
