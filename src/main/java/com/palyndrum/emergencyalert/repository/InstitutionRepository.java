package com.palyndrum.emergencyalert.repository;

import com.palyndrum.emergencyalert.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, String> {

    @Query(value = "select al.* from (\n" +
            "SELECT  *,( 6378 * acos( cos( radians(:latitude) ) * cos( radians(latitude) ) * cos( radians( longitude) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude) ) ) ) AS distance \n" +
            "FROM institution where type=:type\n" +
            ") al\n" +
            "ORDER BY distance asc\n" +
            "LIMIT :numberResult", nativeQuery = true)
    List<Institution> findNearest(@Param("latitude") double latitude, @Param("longitude") double longitude, String type, int numberResult);
}
