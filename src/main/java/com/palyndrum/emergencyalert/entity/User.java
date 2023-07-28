package com.palyndrum.emergencyalert.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "\"users\"", indexes = {@Index(name = "users_phone_idx", columnList = "phone"), @Index(name = "users_email_idx", columnList = "email")})
public class User extends BaseEntity {

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

    @JsonIgnore
    @Column(name = "registration_otp")
    private String registrationOtp;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_otp_send_date")
    private Date lastOtpSendDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<EmergencyNumbers> emergencyNumbers;

    public User(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> map = new LinkedHashMap<>();

        map.put("id", this.id);
        map.put("name", this.name);
        map.put("phone", this.phone);
        map.put("email", this.email);
        map.put("isVerified", this.isVerified);
        return map;
    }
}
