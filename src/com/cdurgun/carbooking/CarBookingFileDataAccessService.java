package com.cdurgun.carbooking;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {
    private final String filePath;
    private static CarBooking[] bookings;
    private static int capacity = 2;
    private static final int capacityMultiplier = 2;
    private static int carIndex;

    static {
        bookings = new CarBooking[capacity];
        carIndex = 0;
    }

    public CarBookingFileDataAccessService(String filePath) throws IOException {
        this.filePath = filePath;
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
                        increaseCapacity();
                        bookings[carIndex++] = booking;
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
    public CarBooking findById(UUID bookingId) {
        for (int i = 0; i < carIndex; i++) {
            if (bookings[i].getId().equals(bookingId)) {
                return bookings[i];
            }
        }
        return null;
    }

    @Override
    public CarBooking[] findAll() {
        return bookings;
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
        increaseCapacity();
        bookings[carIndex++] = carBooking;
    }

    private static void writeBookings(ObjectOutputStream out) throws IOException {
        for (int i = 0; i < carIndex; i++) {
            out.writeObject(bookings[i]);
        }
    }

    @Override
    public void delete(UUID id) {
        CarBooking carBooking = findById(id);
        if (carBooking != null) {
            carBooking.setStatus(BookingStatus.CANCELLED);
        } else {
            return;
        }

        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(filePath))) {
            writeBookings(out);
        } catch (IOException e) {
            carBooking.setStatus(BookingStatus.ACTIVE);
            throw new RuntimeException(
                "Could not cancel car booking with ID: " + id, e
            );
        }
    }

    private void increaseCapacity() {
        if (carIndex >= capacity) {
            capacity *= capacityMultiplier;
            bookings = Arrays.copyOf(bookings, capacity);
        }
    }
}
