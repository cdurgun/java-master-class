package com.cdurgun.car;

import java.math.BigDecimal;
import java.util.UUID;

import static com.cdurgun.car.Brand.*;

public class CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
            new Car(UUID.randomUUID(), "1672890", BigDecimal.valueOf(100), TESLA, true),
            new Car(UUID.randomUUID(), "1672891", BigDecimal.valueOf(200), AUDI, false),
            new Car(UUID.randomUUID(), "1672892", BigDecimal.valueOf(250), MERCEDES, false),
            new Car(UUID.randomUUID(), "1672893", BigDecimal.valueOf(150), TOYOTA, false)
        };
    }

    public static Car[] findAll() {
        return cars;
    }

    public Car findById(UUID carId) {
        for (Car car : cars) {
            if (car.getId().equals(carId))
                return car;
        }
        return null;

    }

    public Car findByRegNumber(String regNumber) {
        for (Car car : cars) {
            if (car.getRegNumber().equals(regNumber))
                return car;
        }
        return null;
    }
}
