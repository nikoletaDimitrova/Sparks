package com.sap.sparks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "staff_cars")
public class StaffCars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @JsonBackReference(value = "staffCars")
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
Users user;

//    @JsonBackReference
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "car_id")
Cars car;
    private enum CarMaintenance {
       repair,
       tire_check
    }

    @Column(name = "car_main")
    @Enumerated(EnumType.STRING)
    private CarMaintenance carMain;

    public StaffCars()
    {
        //default
    }

    public StaffCars(Integer id, Users user, Cars car, CarMaintenance carMain) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.carMain = carMain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users staff) {
        this.user = user;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public CarMaintenance getCarMain() {
        return carMain;
    }

    public void setCarMain(CarMaintenance carMain) {
        this.carMain = carMain;
    }
}