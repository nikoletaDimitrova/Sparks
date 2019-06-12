package com.sap.sparks.controllers;

import com.sap.sparks.entity.Trips;
import com.sap.sparks.repositories.TripsRepository;
import com.sap.sparks.service.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/trips")
public class TripsController {

    @Autowired
    private TripsService tripsService;

//    @Autowired
//    private TripsRepository tripsRepository;

    @GetMapping("/findAllTrips")
    public ResponseEntity<List<Trips>> findAll() {
        List<Trips> allTrips = tripsService.findAll();

        return new ResponseEntity<>(allTrips, HttpStatus.OK);
    }

    @GetMapping("/trip/{id}")
    public ResponseEntity<Trips> getTrip(@PathVariable("id") Integer id) {

        Trips trip = tripsService.findById(id);

        if (trip == null) {
            throw new RuntimeException("Trip id not found - " + id);
        }

        return new ResponseEntity<>(trip, HttpStatus.OK);
    }


    @PostMapping("/addTrip")
    public ResponseEntity<Void> addTrip(@RequestParam("end") String end, @RequestParam("carId") String carId, @RequestParam("userName") String userName) {
       Integer car = Integer.valueOf(carId);
        tripsService.save(end, car, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/updateTrip/{id}")
    public ResponseEntity<Trips> updateTrip(@PathVariable("id") Integer id, @RequestBody Trips trip, @RequestParam("carId") Integer carId, @RequestParam("userName") String userName) {
        Trips existingTrip = tripsService.findById(id);

        if(existingTrip == null){
            throw new RuntimeException("Trip doesn't find with id: " + id);
        }
        trip.setId(existingTrip.getId());
        String end = trip.getEnd();
        tripsService.save(end, carId, userName);

        return new ResponseEntity<>(trip, HttpStatus.OK);
    }


    @DeleteMapping("/deleteTrip/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable("id") int id) {

        Trips tempTrip = tripsService.findById(id);

        if (tempTrip == null) {
            throw new RuntimeException("Trip id not found - " + id);
        }

        tripsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllTripsForUser/{userName}")
    public  ResponseEntity<List<Trips>> tripsPerUser(@PathVariable("userName")String userName){
        List<Trips> allTripsPerUser = tripsService.findByUser(userName);
        allTripsPerUser.sort(Comparator.comparing(Trips::getId).reversed());
        return new ResponseEntity<>(allTripsPerUser, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/stopJourney/{id}")
    public ResponseEntity<Void> stopJourney(@PathVariable("id") Integer id){
       tripsService.deactivateTrip(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }

}