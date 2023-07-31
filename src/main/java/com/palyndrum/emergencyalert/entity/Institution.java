package com.palyndrum.emergencyalert.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(name = "institution")
public class Institution extends BaseEntity {

    @UuidGenerator
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Transient
    private Double distance;

    public Map<String, Object> forListResponse() {

        HashMap<String, Object> map = new LinkedHashMap<>();

        map.put("id", this.id);
        map.put("name", this.name);
        map.put("address", this.address);
        map.put("phone", this.phone);
        map.put("distance", this.distance);

        return map;
    }


}
