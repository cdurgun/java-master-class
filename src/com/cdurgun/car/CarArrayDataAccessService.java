package com.cdurgun.car;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cdurgun.car.Brand.*;

public class CarArrayDataAccessService implements CarDao {
    private static final List<Car> cars;

    static {
        cars = List.of(
            new Car(UUID.fromString("f557677f-8511-486d-a0e3-da8e1a2635ef"), "1672890",
                BigDecimal.valueOf(100), TESLA,
                true),
            new Car(UUID.fromString("b19b873d-5e5a-44b4-8196-acd9c92a3ec2"), "1672891",
                BigDecimal.valueOf(200), AUDI,
                false),
            new Car(UUID.fromString("6c2fda9b-f84c-4bc8-956f-bbf027234526"), "1672892",
                BigDecimal.valueOf(250),
                MERCEDES, false),
            new Car(UUID.fromString("9ffe79a2-3a23-49f6-813e-39edd3c3ea27"), "1672893",
                BigDecimal.valueOf(150), TOYOTA,
                false)
        );
    }

    @Override
    public List<Car> findAll() {
        return cars;
    }

    @Override
    public Optional<Car> findById(UUID carId) {
        return cars.stream()
            .filter(car -> car.getId().equals(carId))
            .findFirst();
    }

    @Override
    public Optional<Car> findByRegNumber(String regNumber) {
        return cars.stream()
            .filter(car -> car.getRegNumber().equals(regNumber))
            .findFirst();
    }
}
