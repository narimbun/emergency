package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.api.payload.request.RegistrationRq;
import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/registration")
public class RegistrationController extends BaseController {


    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<User> registration(@Valid @RequestBody RegistrationRq registrationRq) throws ResourceInvalidException, ResourceConflictException {

        User user = registrationService.registration(registrationRq);

        return ResponseEntity.ok(user);
    }


}
