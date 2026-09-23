package com.cdurgun.carbooking;

import java.util.UUID;

public interface CarBookingDao {
    CarBooking findById(UUID bookingId);

    CarBooking[] findAll();

    void save(CarBooking carBooking);

    void delete(UUID id);
}
