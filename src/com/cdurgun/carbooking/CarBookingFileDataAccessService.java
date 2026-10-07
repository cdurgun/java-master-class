package com.cdurgun.carbooking;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {
    private final String filePath;
    private final List<CarBooking> bookings;

    public CarBookingFileDataAccessService(String filePath) throws IOException {
        this.filePath = filePath;
        this.bookings = new ArrayList<>();
        initializeBookings(filePath);
    }

    private void initializeBookings(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (Files.notExists(path)) {
            Files.createFile(path);
        } else {
            // File exists but has no records
            if (Files.size(path) == 0) {
                return;
            }
            // File exists and contains records
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                while (true) {
                    try {
                        CarBooking booking = (CarBooking) in.readObject();
                        bookings.add(booking);
                    } catch (EOFException e) {
                        break;
                    } catch (ClassNotFoundException e) {
                        throw new IOException("Could not load car bookings.", e);
                    }
                }
            }

        }
    }

    @Override
    public Optional<CarBooking> findById(UUID bookingId) {
        return bookings.stream()
            .filter(booking -> booking.getId().equals(bookingId))
            .findFirst();
    }

    @Override
    public List<CarBooking> findAll() {
        return List.copyOf(bookings);
    }

    @Override
    public void save(CarBooking carBooking) {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(filePath))) {
            writeBookings(out);
            out.writeObject(carBooking);

        } catch (IOException e) {
            throw new RuntimeException("Could not save car booking.", e);
        }
        bookings.add(carBooking);
    }

    private void writeBookings(ObjectOutputStream out) throws IOException {
        for (CarBooking booking : bookings) {
            out.writeObject(booking);
        }
    }

    @Override
    public void delete(UUID id) {
        Optional<CarBooking> carBooking = findById(id)
            .filter(CarBooking::isActive);

        if (carBooking.isPresent()) {
            carBooking.get().setStatus(BookingStatus.CANCELLED);
            try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {
                writeBookings(out);
            } catch (IOException e) {
                carBooking.get().setStatus(BookingStatus.ACTIVE);
                throw new RuntimeException(
                    "Could not cancel car booking with ID: " + id, e
                );
            }
        }
    }

}
