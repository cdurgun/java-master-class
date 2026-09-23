package com.cdurgun.car;

import java.util.UUID;

public interface CarDao {

    Car[] findAll();

    Car findById(UUID carId);

    Car findByRegNumber(String regNumber);

}
