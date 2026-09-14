package com.cdurgun.carbooking;

import java.util.Arrays;
import java.util.UUID;

public class CarBookingDao {
    private static CarBooking[] bookings;
    private static int capacity = 2;
    private static final int capacityMultiplier = 2;
    private static int carIndex;

    static {
        bookings = new CarBooking[capacity];
        carIndex = 0;
    }

    public void save(CarBooking carBooking) {
        if (carIndex >= capacity) {
            capacity *= capacityMultiplier;
            bookings = Arrays.copyOf(bookings, capacity);
        }
        bookings[carIndex++] = carBooking;
    }

    public static CarBooking[] findAll() {
        return bookings;
    }

    public CarBooking findById(UUID bookingId) {
        for (int i = 0; i < carIndex; i++) {
            if (bookings[i].getId().equals(bookingId)) {
                return bookings[i];
            }
        }
        return null;
    }

    public void delete(UUID id) {
        CarBooking carBooking = findById(id);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELLED);
        }
    }
}
