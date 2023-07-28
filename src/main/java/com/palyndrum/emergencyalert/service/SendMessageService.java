package com.palyndrum.emergencyalert.service;

import com.palyndrum.emergencyalert.api.payload.request.EmergencyRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;

public interface SendMessageService {

    void sendMessage(EmergencyRq bodyRq) throws ResourceNotFoundException, ResourceForbiddenException;
}
