package com.palyndrum.emergencyalert.repository;

import com.palyndrum.emergencyalert.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String email);


    @Transactional
    @Modifying
    @Query(value = "update users set last_login_time=now() where id=:id", nativeQuery = true)
    void updateLastLoginTime(@Param("id") String id);
}
