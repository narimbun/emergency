package com.palyndrum.emergencyalert.repository;

import com.palyndrum.emergencyalert.entity.EmergencyNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmergencyNumbersRepository extends JpaRepository<EmergencyNumbers, String> {

    @Query("select a from EmergencyNumbers a where a.user.id=:userId")
    List<EmergencyNumbers> findByUserId(@Param("userId") String userId);

    @Query("select a from EmergencyNumbers a where a.user.id=:userId and a.phone=:phone")
    Optional<EmergencyNumbers> findByUserIdAndPhone(@Param("userId") String userId, @Param("phone") String phone);

}
