package com.palyndrum.emergencyalert.common.security.services;


import com.palyndrum.emergencyalert.common.security.model.UserDetailsImpl;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone number : " + username));

        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPhone(), user.getPassword(), user.getUsername(), user.getName(), user.isActive(), user.getLastLoginTime());
    }

}
