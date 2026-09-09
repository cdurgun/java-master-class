package com.cdurgun.carbooking;

import java.util.Arrays;
import java.util.UUID;

public class CarBookingDao {
    private static CarBooking[] bookings;
    private static int capacity = 2;
    private static final int capacityIncrement = 1;
    private static int carIndex;

    static {
        bookings = new CarBooking[capacity];
        carIndex = 0;
    }

    public void save(CarBooking carBooking)  {
        if (carIndex >=  capacity) {
            capacity += capacityIncrement;
            bookings = Arrays.copyOf(bookings, capacity);
        }
        bookings[carIndex++] = carBooking;
    }

    public static CarBooking[] findAll() {
        return bookings;
    }

    public CarBooking findById(UUID bookingId) {
        for(CarBooking carBooking: bookings) {
            if(carBooking.getId().equals(bookingId))
                return carBooking;
        }
        return null;
    }

    public void delete(CarBooking carBooking) {
        carBooking.setStatus(BookingStatus.CANCELLED);
    }
}
