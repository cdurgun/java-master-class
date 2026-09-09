package com.cdurgun.car;


import java.util.UUID;

public class CarService {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public Car[] getAllCars() {
        return carDao.findAll();
    }

    public Car getCarByID(UUID carId) {
        return carDao.findById(carId);
    }

    public Car getCarByRegNumber(String regNumber) {
        return carDao.findByRegNumber(regNumber);
    }

}
