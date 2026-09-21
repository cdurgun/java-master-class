package com.cdurgun;

import com.cdurgun.car.Car;
import com.cdurgun.car.CarArrayDataAccessService;
import com.cdurgun.car.CarDao;
import com.cdurgun.car.CarService;
import com.cdurgun.carbooking.*;
import com.cdurgun.user.User;
import com.cdurgun.user.UserArrayDataAccessService;
import com.cdurgun.user.UserDao;
import com.cdurgun.user.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    static void main(String[] args) {

        UserDao userDao = new UserArrayDataAccessService();
        UserService userService = new UserService(userDao);
        CarDao carDao = new CarArrayDataAccessService();
        CarService carService = new CarService(carDao);
        CarBookingDao carBookingDao = null;

        int implOption = 2;
        switch (implOption) {
            case 1 -> {
                carBookingDao = new CarBookingArrayDataAccessService();
            }
            case 2 -> {
                try {
                    String filePath = "bookings.dat";
                    // Depending on the argument, the file can be deleted at the start of each session.
                    if (args.length > 0 && args[0].equalsIgnoreCase("delete")) {
                        Files.deleteIfExists(Path.of(filePath));
                    }
                    carBookingDao = new CarBookingFileDataAccessService(filePath);
                } catch (RuntimeException | IOException e) {
                    System.out.println("An error occurred while initializing the booking data:" + e.getMessage());
                    return;
                }
            }
        }

        CarBookingService carBookingService = new CarBookingService(carBookingDao, userService, carService);
        processMenuOption(userService, carService, carBookingService);
    }

    private static void processMenuOption(UserService userService, CarService carService, CarBookingService carBookingService) {
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            while (option != 8) {
                showMenu();
                try {
                    option = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid Option !!!");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();

                switch (option) {
                    case 1 -> displayCarBookingAndSave(userService, carService, carBookingService, scanner);
                    case 2 -> deleteCarBooking(carBookingService, scanner);
                    case 3 -> displayAllUserBookedCars(userService, carBookingService, scanner);
                    case 4 -> displayAllBookings(carBookingService);
                    case 5 -> displayAllAvailableCars(carService, carBookingService, false);
                    case 6 -> displayAllAvailableCars(carService, carBookingService, true);
                    case 7 -> displayUsers(userService);
                    case 8 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid Option !!!");
                }

            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred  :" + e.getMessage());
        }
        scanner.close();
    }


    private static void showMenu() {
        System.out.println("       Car Booking CLI System         ");
        System.out.println("---------------------------------------");
        System.out.println("|    1 - Book Car                     |");
        System.out.println("|    2 - Delete Booking               |");
        System.out.println("|    3 - View All User Booked Cars    |");
        System.out.println("|    4 - View All Bookings            |");
        System.out.println("|    5 - View Available Cars          |");
        System.out.println("|    6 - View Available Electric Cars |");
        System.out.println("|    7 - View All Users               |");
        System.out.println("|    8 - Exit                         |");
        System.out.println("---------------------------------------");
        System.out.println("|     Select an option ....?          |");
    }

    private static void displayAllUserBookedCars(UserService userService, CarBookingService carBookingService, Scanner scanner) throws RuntimeException {
        boolean isBooked = false;
        displayUsers(userService);
        System.out.print("Enter User Id : ");
        String userId = scanner.nextLine();
        try {
            if (!userService.userExists(UUID.fromString(userId))) {
                System.out.println("User not found");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user id :" + userId);
            return;
        }

        for (CarBooking carBooking : carBookingService.getAllCarBookings()) {
            if (carBooking != null && carBooking.getUser().getId().equals(
                UUID.fromString(userId)) && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                isBooked = true;
                System.out.println(carBooking.getCar() + " is booked by " + carBooking.getUser());
            }
        }
        if (!isBooked) {
            System.out.println("No bookings");
        }
        System.out.println();

    }

    private static boolean displayAllBookings(CarBookingService carBookingService) throws RuntimeException {
        boolean isBooked = false;
        for (CarBooking carBooking : carBookingService.getAllCarBookings()) {
            if (carBooking != null && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                isBooked = true;
                System.out.println("Booking :" + carBooking);
            }
        }
        if (!isBooked) {
            System.out.println("No bookings");
        }
        System.out.println();
        return isBooked;
    }

    private static boolean displayAllAvailableCars(CarService carService, CarBookingService carBookingService, boolean isElectricCar) throws RuntimeException {
        boolean existAvailableCar = false;
        System.out.println("                               Car List                                           ");
        System.out.println("----------------------------------------------------------------------------------");
        for (Car car : carService.getAllCars()) {
            if (isElectricCar) {
                if (!carBookingService.isCarBooked(car.getRegNumber()) && car.isElectric()) {
                    existAvailableCar = true;
                    System.out.println(car);
                }
            } else {
                if (!carBookingService.isCarBooked(car.getRegNumber())) {
                    existAvailableCar = true;
                    System.out.println(car);
                }
            }
        }
        if (!existAvailableCar) {
            System.out.println("No cars available for booking !!!");
        }
        System.out.println("----------------------------------------------------------------------------------");
        return existAvailableCar;
    }


    private static void displayCarBookingAndSave(UserService userService, CarService carService, CarBookingService carBookingService, Scanner scanner) throws RuntimeException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            boolean carExists = displayAllAvailableCars(carService, carBookingService, false);
            if (!carExists) {
                break;
            }
            System.out.println("Select car reg number ");
            String regNumber = scanner.nextLine();
            Car car = carService.getCarByRegNumber(regNumber);
            if (car == null) {
                System.out.println("Car not found");
                break;
            }

            if (carBookingService.isCarBooked(regNumber)) {
                System.out.println("This car has already been booked !!!");
                break;
            }

            UUID carId = car.getId();

            displayUsers(userService);
            System.out.println("Select user id ");
            String userId = scanner.nextLine();
            try {
                if (!userService.userExists(UUID.fromString(userId))) {
                    System.out.println("User not found");
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid user id :" + userId);
                break;
            }

            String startDateStr;
            LocalDate startDate;
            while (true) {
                try {
                    System.out.println("Select start date(dd-mm-yyyy)");
                    startDateStr = scanner.nextLine();
                    startDate = LocalDate.parse(startDateStr, dateTimeFormatter);

                    if (startDate.isBefore(LocalDate.now())) {
                        System.out.println("Start date must not be in the past !!!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Date, please try again");
                }
            }

            String endDateStr;
            LocalDate endDate;
            while (true) {
                try {
                    System.out.println("Select end date(dd-mm-yyyy)");
                    endDateStr = scanner.nextLine();
                    endDate = LocalDate.parse(endDateStr, dateTimeFormatter);
                    if (endDate.isBefore(startDate)) {
                        System.out.println("End date must be after startDate !!!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Date, please try again");
                }
            }

            System.out.println("Would you like to save this booking (Y/N)");
            String saveBooking = scanner.nextLine();
            if (saveBooking.equalsIgnoreCase("Y")) {
                CarBooking carBooking;
                try {
                    carBooking = carBookingService.bookCar(UUID.fromString(userId), carId, startDate, endDate);
                } catch (RuntimeException e) {
                    System.out.println("Car booking failed :" + e.getMessage());
                    carBooking = null;
                }
                if (carBooking != null) {
                    System.out.println("Car booked successfully :" + carBooking);
                }
            }
            break;
        }
    }

    private static void deleteCarBooking(CarBookingService carBookingService, Scanner scanner) throws RuntimeException {
        boolean existBookings = displayAllBookings(carBookingService);
        if (!existBookings) return;
        System.out.println("Enter booking id");
        String bookingId = scanner.nextLine();
        CarBooking carBooking;
        try {
            carBooking = carBookingService.getCarBookingById(UUID.fromString(bookingId));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid booking id :" + bookingId);
            return;
        }

        if (carBooking != null) {
            carBookingService.deleteCarBooking(carBooking);
            System.out.println("Deleted:" + carBooking);
        } else {
            System.out.println("Car booking not found !!!");
        }
    }

    private static void displayUsers(UserService userService) {
        System.out.println("                       User List                                ");
        System.out.println("----------------------------------------------------------------");
        User[] users = userService.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("----------------------------------------------------------------");
    }
}