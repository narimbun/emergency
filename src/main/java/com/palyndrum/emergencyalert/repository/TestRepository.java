package com.palyndrum.emergencyalert.repository;

import com.palyndrum.emergencyalert.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, String> {
}
