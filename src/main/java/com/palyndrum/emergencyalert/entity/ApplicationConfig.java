package com.palyndrum.emergencyalert.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "application_config")
public class ApplicationConfig extends BaseEntity {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String value;
}
