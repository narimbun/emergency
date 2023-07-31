package com.palyndrum.emergencyalert.repository;

import com.palyndrum.emergencyalert.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, String> {

    @Query(value = "select id,name,address,phone,distance from (\n" +
            "SELECT  *,( 6378 * acos( cos( radians(:latitude) ) * cos( radians(latitude) ) * cos( radians( longitude) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude) ) ) ) AS distance \n" +
            "FROM institution where type=:type\n" +
            ") al\n" +
            "ORDER BY distance asc\n" +
            "LIMIT :numberResult", nativeQuery = true)
    List<Object[]> findNearest(@Param("latitude") double latitude, @Param("longitude") double longitude, String type, int numberResult);

    @Query(value = "select id,name,address,phone,distance from (\n" +
            "SELECT  *,( 6378 * acos( cos( radians(:latitude) ) * cos( radians(latitude) ) * cos( radians( longitude) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude) ) ) ) AS distance \n" +
            "FROM institution\n" +
            ") al\n" +
            "ORDER BY distance asc\n" +
            "LIMIT :numberResult", nativeQuery = true)
    List<Object[]> findNearest(@Param("latitude") double latitude, @Param("longitude") double longitude, int numberResult);
}
