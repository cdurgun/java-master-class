package com.cdurgun.car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarDao {

    List<Car> findAll();

    Optional<Car> findById(UUID carId);

    Optional<Car> findByRegNumber(String regNumber);

}
