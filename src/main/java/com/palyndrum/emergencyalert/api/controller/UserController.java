package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.api.payload.request.VerificationRq;
import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.common.api.payload.response.CommonRs;
import com.palyndrum.emergencyalert.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile() throws ResourceNotFoundException {

        return ResponseEntity.ok(userService.profile().toMap());
    }

    @PostMapping("/verification")
    public ResponseEntity<CommonRs> verification(@Valid @RequestBody VerificationRq bodyRq) throws ResourceNotFoundException, ResourceInvalidException, ResourceConflictException {

        userService.verification(bodyRq);

        return ResponseEntity.ok(new CommonRs("Account anda berhasil di-verifikasi"));
    }

    @PostMapping("/verification/send")
    public ResponseEntity<CommonRs> sendVerification() throws ResourceNotFoundException, ResourceConflictException, ResourceForbiddenException {

        return ResponseEntity.ok(new CommonRs(userService.sendVerification()));
    }


}
