package com.sap.sparks.controllers;

import com.sap.sparks.entity.Cars;
import com.sap.sparks.entity.Users;
import com.sap.sparks.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/cars")
public class CarsController {

    @Autowired
    private CarsService carsService;

    @Autowired
    private GenericWebApplicationContext context;


    @CrossOrigin
    @GetMapping("/findAllAvailableCars")
    public ResponseEntity<List<Cars>> findAllAvailable(@RequestParam("city") String city) {
        List<Cars> availableCars  = carsService.findByIsAvailable(city);
        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }


    @CrossOrigin("http://localhost:4200")
    @GetMapping("/findAllCars")
    public ResponseEntity<List<Cars>> findAll() {
        List<Cars> allCars = carsService.findAll();
        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Cars> getCar(@PathVariable int id) {

        Cars car = carsService.findById(id);

        if (car == null) {
            throw new RuntimeException("Car id not found - " + id);
        }
      return new ResponseEntity<>(car, HttpStatus.OK);
    }


    @CrossOrigin
    @PostMapping("/addCar")
    public ResponseEntity<Cars> addCar(@RequestBody Cars car) {
        carsService.save(car);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }


    @PutMapping("/updateCar/{id}")
    public ResponseEntity<Cars> updateCar(@PathVariable("id") Integer id, @RequestBody Cars car) {
        Cars existingCar = carsService.findById(id);
        if(existingCar == null){
            throw new RuntimeException("Car id not found - " + id);
        }
        car.setId(id);
        carsService.save(car);
        return new ResponseEntity<>(car, HttpStatus.OK) ;
    }


    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {

        Cars tempCar = carsService.findById(id);

        if (tempCar == null) {
            throw new RuntimeException("Car id not found - " + id);
        }

        carsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllCities")
    public ResponseEntity<List<String>> findAllCities(){
        List<String> allCities = carsService.findAllCities();
        return new ResponseEntity<>(allCities, HttpStatus.OK);
    }

    @PutMapping("releaseCar/{id}")
    public ResponseEntity<Cars> releaseCar(@PathVariable("id") Integer id){
        Cars car = carsService.findById(id);
        if(car == null){
            throw new RuntimeException("Car was not found");
        }
        car.setIsAvailable(true);
        carsService.save(car);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

}
