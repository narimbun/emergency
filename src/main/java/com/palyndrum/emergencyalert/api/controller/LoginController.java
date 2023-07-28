package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.api.payload.request.LoginRq;
import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.common.security.jwt.JWTUtils;
import com.palyndrum.emergencyalert.common.security.model.UserDetailsImpl;
import com.palyndrum.emergencyalert.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController extends BaseController {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    public LoginController(UserRepository userRepository, AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authentication(@Valid @RequestBody LoginRq bodyRq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(bodyRq.getUsername(), bodyRq.getPassword()));

        Map<String, String> jwt = jwtUtils.generateAccessToken(authentication);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("token", jwt.get("accessToken"));
        response.put("expiration", Integer.parseInt(jwt.get("expiration")));
        response.put("type", "Bearer");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        userRepository.updateLastLoginTime(userDetails.getId());

        return ResponseEntity.ok(response);
    }

}
