package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.api.payload.request.RegistrationRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.UserRepository;
import com.palyndrum.emergencyalert.service.RegistrationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registration(RegistrationRq registrationRq) throws ResourceInvalidException {

        if (!registrationRq.getPassword().equals(registrationRq.getConfirmPassword()))
            throw ResourceInvalidException.create("Password and Confirm Password doesn't match.");

        User user = new User();

        user.setEmail(registrationRq.getEmail());
        user.setPhone(registrationRq.getPhone());
        user.setName(registrationRq.getName());
        user.setUsername(registrationRq.getPhone());
        user.setPassword(passwordEncoder.encode(registrationRq.getPassword()));
        user.setActive(true);
        user.setVerified(false);

        user = userRepository.save(user);

        return user;
    }
}
