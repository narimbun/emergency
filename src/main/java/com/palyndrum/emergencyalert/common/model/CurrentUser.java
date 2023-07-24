package com.palyndrum.emergencyalert.common.model;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Date;

@Component
@RequestScope
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrentUser {

    private String id;
    private String email;
    private String phone;
    private String username;
    private String name;
    private boolean isActive;
    private Date lastLoginTime;
}
