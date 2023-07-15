package com.palyndrum.emergencyalert.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "created_by_id")
    private String createdById;

    @JsonIgnore
    @Column(name = "updated_by_id")
    private String updatedById;

    @JsonIgnore
    @Column(name = "created_by_name")
    private String createdByName;

    @JsonIgnore
    @Column(name = "updated_by_name")
    private String updatedByName;

}
