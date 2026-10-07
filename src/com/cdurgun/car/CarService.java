package com.cdurgun.car;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarService {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getAllCars() {
        return carDao.findAll();
    }

    public Optional<Car> getCarByID(UUID carId) {
        return carDao.findById(carId);
    }

    public Optional<Car> getCarByRegNumber(String regNumber) {
        return carDao.findByRegNumber(regNumber);
    }

}
