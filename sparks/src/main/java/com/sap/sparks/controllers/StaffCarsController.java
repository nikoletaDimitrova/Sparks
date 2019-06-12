package com.sap.sparks.controllers;

import com.sap.sparks.entity.StaffCars;
import com.sap.sparks.service.StaffCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/staffcars")
public class StaffCarsController {

    @Autowired
    private StaffCarsService staffCarsService;


    @GetMapping("/findAllStaffCars")
    public ResponseEntity<List<StaffCars>> findAll() {
        List<StaffCars> staffCars = staffCarsService.findAll();

        return new ResponseEntity<>(staffCars, HttpStatus.OK);
    }

    @GetMapping("/staffCar/{id}")
    public ResponseEntity<StaffCars> getStaffCar(@PathVariable int id) {

        StaffCars staffCar = staffCarsService.findById(id);

        if (staffCar == null) {
            throw new RuntimeException("StaffCar id not found - " + id);
        }

        return new ResponseEntity<>(staffCar, HttpStatus.OK);
    }


    @PostMapping("/addStaffCars")
    public ResponseEntity<StaffCars> addStaffCar(@RequestBody StaffCars staffCar) {
        if(staffCar == null){
            //exception
        }
        staffCarsService.save(staffCar);
        return new ResponseEntity<>(staffCar, HttpStatus.OK);
    }


    @PutMapping("/updateStaffCars/{id}")
    public ResponseEntity<StaffCars> updateStaffCar(@PathVariable("id") Integer id, @RequestBody StaffCars staffCar) {
        StaffCars existingStaffCar = staffCarsService.findById(id);
        if(existingStaffCar == null){
            throw  new RuntimeException("StaffCar doesnt exist with id: " + id);
        }

        staffCar.setId(existingStaffCar.getId());
        staffCarsService.save(staffCar);

        return new ResponseEntity<>(staffCar, HttpStatus.OK);
    }


    @DeleteMapping("/deleteStaffCar/{id}")
    public ResponseEntity<Void> deleteStaffCar(@PathVariable("id") int id) {

        StaffCars tempStaffCar = staffCarsService.findById(id);

        if (tempStaffCar == null) {
            throw new RuntimeException("StaffCar id not found - " + id);
        }

        staffCarsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
