package com.palyndrum.emergencyalert.service;

import com.palyndrum.emergencyalert.api.payload.request.RecipientRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface EmergencyNumbersService {

    Map<String, Object> addNumber(RecipientRq bodyRq) throws ResourceInvalidException, ResourceConflictException;

    Map<String, Object> editNumber(String id, RecipientRq bodyRq) throws ResourceNotFoundException, ResourceInvalidException, ResourceConflictException;

    List<Map<String, Object>> numberList();

    void deleteNumber(String id) throws ResourceNotFoundException;
}
