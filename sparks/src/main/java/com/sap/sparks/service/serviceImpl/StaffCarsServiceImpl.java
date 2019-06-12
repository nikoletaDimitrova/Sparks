package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.entity.Cars;
import com.sap.sparks.entity.StaffCars;
import com.sap.sparks.entity.Users;
import com.sap.sparks.repositories.CarsRepository;
import com.sap.sparks.repositories.StaffCarsRepository;
import com.sap.sparks.repositories.UsersRepository;
import com.sap.sparks.service.CarsService;
import com.sap.sparks.service.StaffCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.List;
import java.util.Optional;


@Service
public class StaffCarsServiceImpl implements StaffCarsService {


    private StaffCarsRepository staffCarsRepository;

    @Autowired
    GenericWebApplicationContext mediator;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CarsService carsService;

    @Autowired
    public StaffCarsServiceImpl(StaffCarsRepository staffCarsRepository) {
        this.staffCarsRepository = staffCarsRepository;
    }

    @Override
    public List<StaffCars> findAll() {
        return staffCarsRepository.findAll();
    }

    @Override
    public StaffCars findById(int id) {
        Optional<StaffCars> result = staffCarsRepository.findById(id);

        StaffCars staffCar = null;

        if (result.isPresent()) {
            staffCar = result.get();
        } else {
            throw new RuntimeException("Did not find staffCars id - " + id);
        }

        return staffCar;
    }

    @Override
    public void save(StaffCars staffCar) {
        Users staffUser = (Users)mediator.getBean("usertemp");
        staffCar.setUser(staffUser);
        List<StaffCars> userStaffList = staffUser.getStaffCars();
        userStaffList.add(staffCar);
        staffUser.setStaffCars(userStaffList);
        Cars car = carsService.findById(staffCar.getCar().getId());
        staffCar.setCar(car);
        List<StaffCars> carStaffList = car.getStaffCars();
        carStaffList.add(staffCar);
        car.setStaffCars(carStaffList);
        carsService.save(car);
        usersRepository.save(staffUser);
        staffCarsRepository.save(staffCar);
    }

    @Override
    public void deleteById(int theId) {
        staffCarsRepository.deleteById(theId);
    }

}


