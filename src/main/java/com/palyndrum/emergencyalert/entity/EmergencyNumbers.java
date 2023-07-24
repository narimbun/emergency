package com.palyndrum.emergencyalert.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "emergency_numbers", indexes = {@Index(name = "emergency_number_phone_idx", columnList = "phone"), @Index(name = "emergency_number_name_idx", columnList = "name")})
public class EmergencyNumbers extends BaseEntity {

    @UuidGenerator
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @JsonIgnore
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    public Map<String, Object> toMap() {

        HashMap<String, Object> map = new LinkedHashMap<>();

        map.put("id", this.id);
        map.put("name", this.name);
        map.put("phone", this.phone);
        map.put("isActive", this.isActive);
        map.put("createdDate", getCreatedDate());
        map.put("updatedDate", getUpdatedDate());

        return map;
    }

}
