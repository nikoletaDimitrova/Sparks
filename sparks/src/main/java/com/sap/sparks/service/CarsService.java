package com.sap.sparks.service;

import com.sap.sparks.entity.Cars;

import java.util.List;

public interface CarsService {

    List<Cars> findAll();

    Cars findById(int id);

    void save(Cars car);

    void deleteById(int id);

    List<Cars> findByIsAvailable(String city);

    List<String> findAllCities();


}


