package com.cdurgun.car;

import java.util.List;
import java.util.UUID;

public interface CarDao {

    List<Car> findAll();

    Car findById(UUID carId);

    Car findByRegNumber(String regNumber);

}
