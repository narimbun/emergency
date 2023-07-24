package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.api.payload.request.RegistrationRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.constant.RegexConstant;
import com.palyndrum.emergencyalert.common.util.CommonUtil;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.UserRepository;
import com.palyndrum.emergencyalert.service.RegistrationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registration(RegistrationRq registrationRq) throws ResourceInvalidException, ResourceConflictException {


        if(!CommonUtil.patternMatches(registrationRq.getEmail(), RegexConstant.EMAIL_PATTERN))
            throw ResourceInvalidException.create("Email tidak valid. Pastikan Anda memasukan email yang benar.");

        if(!CommonUtil.patternMatches(registrationRq.getPhone(), RegexConstant.PHONE_PATTERN))
            throw ResourceInvalidException.create("Nomor handphone tidak valid. Pastikan Anda memasukan nomor yang benar.");

        if (!registrationRq.getPassword().equals(registrationRq.getConfirmPassword()))
            throw ResourceInvalidException.create("Password dan Confirm Password tidak sama.");

        if (userRepository.findByEmail(registrationRq.getEmail()).isPresent())
            throw ResourceConflictException.create(String.format("Email %s sudah terdaftar. Gunakan email yang lain.", registrationRq.getEmail()));

        if (userRepository.findByPhone(registrationRq.getPhone()).isPresent())
            throw ResourceConflictException.create(String.format("Nomor handphone %s sudah terdaftar. Gunakan nomor yang lain.", registrationRq.getPhone()));

        User user = new User();

        user.setEmail(registrationRq.getEmail());
        user.setPhone(registrationRq.getPhone());
        user.setName(registrationRq.getName());
        user.setUsername(registrationRq.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRq.getPassword()));
        user.setActive(true);
        user.setVerified(false);
        user.setCreatedDate(new Date());

        user = userRepository.save(user);

        return user;
    }
}
