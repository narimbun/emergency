package com.palyndrum.emergencyalert.service;

import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;

public interface WhatsAppService {

    void sendText(String text, String destination) throws ResourceNotFoundException;
}
