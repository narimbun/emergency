package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/profile")
public class UserController extends BaseController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> profile() throws ResourceNotFoundException {

        return ResponseEntity.ok(userService.profile().toMap());
    }


}
