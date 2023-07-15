package com.palyndrum.emergencyalert.service;

import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.entity.User;

public interface UserService {

    User findById(String id) throws ResourceNotFoundException;
}
