package com.sap.sparks.repositories;

import com.sap.sparks.entity.StaffCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffCarsRepository extends JpaRepository<StaffCars, Integer> {
}
