package com.palyndrum.emergencyalert.repository;

import com.palyndrum.emergencyalert.entity.ApplicationConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationConfigRepository extends JpaRepository<ApplicationConfig, String> {
}
