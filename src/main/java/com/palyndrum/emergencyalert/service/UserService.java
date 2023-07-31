package com.palyndrum.emergencyalert.service;

import com.palyndrum.emergencyalert.api.payload.request.ProfileRq;
import com.palyndrum.emergencyalert.api.payload.request.VerificationRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.entity.User;

public interface UserService {

    User findById(String id) throws ResourceNotFoundException;

    User profile() throws ResourceNotFoundException;

    User editProfile(ProfileRq bodyRq) throws ResourceNotFoundException;

    void verification(VerificationRq bodyRq) throws ResourceNotFoundException, ResourceInvalidException, ResourceConflictException;

    String sendVerification() throws ResourceNotFoundException, ResourceConflictException, ResourceForbiddenException;
}
