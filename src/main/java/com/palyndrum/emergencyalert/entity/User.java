package com.palyndrum.emergencyalert.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "\"users\"", indexes = {@Index(name = "users_phone_idx", columnList = "phone"), @Index(name = "users_email_idx", columnList = "email")})
public class User {

    @UuidGenerator
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Column(name = "is_verified", columnDefinition = "boolean default false")
    private boolean isVerified = true;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time")
    private Date lastLoginTime;

}
