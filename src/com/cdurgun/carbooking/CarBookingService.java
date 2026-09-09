package com.cdurgun.carbooking;

import com.cdurgun.car.Car;
import com.cdurgun.car.CarService;
import com.cdurgun.user.User;
import com.cdurgun.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    private final CarBookingDao carBookingDao;
    private final UserService userService;
    private final CarService carService;


    public CarBookingService(CarBookingDao carBookingDao, UserService userService, CarService carService) {
        this.carBookingDao = carBookingDao;
        this.userService = userService;
        this.carService = carService;
    }

    public CarBooking [] getAllCarBookings() {
        return carBookingDao.findAll();
    }

    public boolean carBooked(String regNumber) {
        for(CarBooking carBooking:this.getAllCarBookings()) {
            if(carBooking!=null && carBooking.getCar().getRegNumber().equals(regNumber)
                && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                return true;
            }
        }
        return false;
    }

    public CarBooking getCarBookingById(UUID bookingId) {
        return carBookingDao.findById(bookingId);
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        Car car = carService.getCarByID(carId);
        User user = userService.getUserById(userId);
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        if(startDate.isEqual(endDate)) {
            numberOfDays = 1;
        }

        BigDecimal rentPrice =  car.getRentalPricePerDay().multiply(BigDecimal.valueOf(numberOfDays));
        CarBooking carBooking = new CarBooking(UUID.randomUUID(),
                                              user, car, startDate,
                                              endDate, rentPrice, BookingStatus.ACTIVE, LocalDate.now());

        carBookingDao.save(carBooking);
        return carBooking;
    }


    public void deleteCarBooking(CarBooking carBooking) {
        carBookingDao.delete(carBooking);
    }

}
