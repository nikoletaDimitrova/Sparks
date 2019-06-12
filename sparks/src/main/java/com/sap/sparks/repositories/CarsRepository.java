package com.sap.sparks.repositories;

import com.sap.sparks.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Integer> {
    @Query("select c from Cars c "+
            "where( c.city = ?1 and c.isAvailable = true)")
    List<Cars> findByIsAvailable(String city);

    @Query("select distinct city from Cars")
    List<String> findAllCities();



}
