package com.sap.sparks.service;

import com.sap.sparks.entity.Trips;

import java.util.List;

public interface TripsService {

    List<Trips> findAll();

    Trips findById(int id);

    void save(String end, Integer carId, String userName);

    void deleteById(int id);

    List<Trips> findByUser(String userName);

    void deactivateTrip(Integer id);

    List<Trips> findTripsPerMonth(Integer month, Integer year, String userName);

    List<Trips> findTripsPerDay(Integer day, Integer month, Integer year, String userName);
}



