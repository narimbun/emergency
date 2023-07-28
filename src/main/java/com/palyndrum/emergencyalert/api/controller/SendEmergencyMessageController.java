package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.api.payload.request.EmergencyRq;
import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.service.SendMessageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/send-message")
public class SendEmergencyMessageController extends BaseController {


    private SendMessageService sendMessageService;

    public SendEmergencyMessageController(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> sendEmergencyMessage(@Valid @RequestBody EmergencyRq bodyRq) throws ResourceNotFoundException, ResourceForbiddenException {

        sendMessageService.sendMessage(bodyRq);

        return ResponseEntity.ok(null);
    }


}
