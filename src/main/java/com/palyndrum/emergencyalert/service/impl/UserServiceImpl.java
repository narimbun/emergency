package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.UserRepository;
import com.palyndrum.emergencyalert.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findById(String id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create(String.format("User with '%s' doesn't exist.", id)));

    }
}
