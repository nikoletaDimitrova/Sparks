package com.sap.sparks.repositories;

import com.sap.sparks.entity.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TripsRepository extends JpaRepository<Trips, Integer> {

    @Query("select t from Trips t " +
            "join Users u on t.user = u.id " +
            "where u.userName = ?1")
    List<Trips> findByUser(String userName);

    @Query("select t from Trips t " +
            "join Users u on t.user = u.id " +
            "where u.userName =?3 and MONTH(t.date) = ?1 and YEAR(t.date) = ?2")
    List<Trips> findTripsPerMonth(Integer month, Integer year, String userName);


    @Query("select t from Trips t " +
            "join Users u on t.user = u.id " +
            "where u.userName =?4 and DAY(t.date) = ?1 and MONTH(t.date) = ?2 and YEAR(t.date) = ?3")
    List<Trips> findTripsPerDay(Integer day, Integer month, Integer year, String userName);
}
