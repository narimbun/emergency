package com.palyndrum.emergencyalert.common.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class UserDetailsImpl implements UserDetails {

    private String id;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String name;
    private boolean isActive;
    private Date lastLoginTime;

    public UserDetailsImpl(String id, String email, String phone, String password, String username, String name, boolean isActive, Date lastLoginTime) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.name = name;
        this.isActive = isActive;
        this.lastLoginTime = lastLoginTime;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
