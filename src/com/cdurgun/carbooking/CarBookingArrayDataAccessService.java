package com.cdurgun.carbooking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private final List<CarBooking> bookings=new ArrayList<>();

    @Override
    public void save(CarBooking carBooking) {
        bookings.add(carBooking);
    }

    @Override
    public List<CarBooking> findAll() {
        return List.copyOf(bookings);
    }

    @Override
    public CarBooking findById(UUID bookingId) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(bookingId)) {
                return bookings.get(i);
            }
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        CarBooking carBooking = findById(id);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELLED);
        }
    }
}
