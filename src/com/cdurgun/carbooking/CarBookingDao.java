package com.cdurgun.carbooking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDao {
    CarBooking findById(UUID bookingId);

    List<CarBooking> findAll();

    void save(CarBooking carBooking);

    void delete(UUID id);
}
