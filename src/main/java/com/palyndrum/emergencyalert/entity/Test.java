package com.palyndrum.emergencyalert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "test")
public class Test extends BaseEntity  {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "text1")
    private String tex1;

    @Column(name = "text2")
    private String tex2;

    @Column(name = "text3")
    private String tex3;
}
