package com.cdurgun.carbooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private final List<CarBooking> bookings = new ArrayList<>();

    @Override
    public void save(CarBooking carBooking) {
        bookings.add(carBooking);
    }

    @Override
    public List<CarBooking> findAll() {
        return List.copyOf(bookings);
    }

    @Override
    public Optional<CarBooking> findById(UUID bookingId) {
        return bookings.stream()
            .filter(cb -> cb.getId().equals(bookingId))
            .findFirst();
    }

    @Override
    public void delete(UUID id) {
        Optional<CarBooking> carBooking = findById(id);
        carBooking.ifPresent(booking -> booking.setStatus(BookingStatus.CANCELLED));
    }
}
