package com.sap.sparks.service;

import com.sap.sparks.entity.StaffCars;

import java.util.List;

public interface StaffCarsService {

    List<StaffCars> findAll();

    StaffCars findById(int id);

    void save(StaffCars staffcar);

    void deleteById(int id);

}


