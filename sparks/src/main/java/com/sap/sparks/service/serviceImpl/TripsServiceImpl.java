package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.entity.Cars;
import com.sap.sparks.entity.Trips;
import com.sap.sparks.entity.Users;
import com.sap.sparks.repositories.CarsRepository;
import com.sap.sparks.repositories.TripsRepository;
import com.sap.sparks.repositories.UsersRepository;
import com.sap.sparks.security.jwt.JwtProvider;
import com.sap.sparks.service.CarsService;
import com.sap.sparks.service.TripsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.List;
import java.util.Optional;


@Service
public class TripsServiceImpl implements TripsService {


    private static final Logger logger = LoggerFactory.getLogger(TripsServiceImpl.class);

    private TripsRepository tripsRepository;

    @Autowired
    private CarsService carsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    GenericWebApplicationContext mediator;

    @Autowired
    public TripsServiceImpl(TripsRepository tripsRepository) {
        this.tripsRepository = tripsRepository;
    }

    @Override
    public List<Trips> findAll() {
        return tripsRepository.findAll();
    }

    @Override
    public Trips findById(int id) {
        Optional<Trips> result = tripsRepository.findById(id);

        Trips trip = null;

        if (result.isPresent()) {
            trip = result.get();
        } else {
            throw new RuntimeException("Did not find trip id - " + id);
        }

        return trip;
    }


    @Override
    public void save(String end, Integer carId, String userName) {
//        trip.setUser((Users)mediator.getBean("usertemp"));
        Trips trip = new Trips();
        Cars car = carsService.findById(carId);
        Users user = usersRepository.findByUserName(userName);
        logger.info(car.toString());
        trip.setPrice(10.00);
        trip.setStart(car.getAddress());
        trip.setActive(true);
        trip.setCar(car);
        trip.setUser(user);
        if(!end.equals("")) {
            trip.setEnd(end);
        }
        tripsRepository.save(trip);
        car.setIsAvailable(false);
        car.setAddress(trip.getEnd());
        car.setBattery(car.getBattery()-20);
        List<Trips> tripList = car.getTrips();
        tripList.add(trip);
        car.setTrips(tripList);
        carsService.save(car);
//        mediator.registerBean("cartemp", Cars.class, () -> car);

    }

    @Override
    public void deleteById(int theId) {
        tripsRepository.deleteById(theId);
    }

    @Override
    public List<Trips> findByUser(String userName) {
        return tripsRepository.findByUser(userName);
    }

    @Override
    public void deactivateTrip(Integer id){
        Trips trip = findById(id);
        trip.setActive(false);
        Cars car = trip.getCar();
        car.setIsAvailable(true);
        tripsRepository.save(trip);
        carsService.save(car);
    }

    @Override
   public List<Trips> findTripsPerMonth(Integer month, Integer year, String userName){
        return tripsRepository.findTripsPerMonth(month, year, userName);
    }

    @Override
    public List<Trips> findTripsPerDay(Integer day, Integer month, Integer year, String userName){
        return tripsRepository.findTripsPerDay(day, month, year, userName);
    }
}


