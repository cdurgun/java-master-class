package com.cdurgun.carbooking;

import com.cdurgun.car.Car;
import com.cdurgun.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class CarBooking {
    private UUID id;
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus status;
    private LocalDate bookedAt;

    public CarBooking(UUID id, User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price, BookingStatus status, LocalDate bookedAt) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDate getBookedAt() {
        return bookedAt;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CarBooking that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user,
            that.user) && Objects.equals(car, that.car) && Objects.equals(startDate,
            that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(price,
            that.price) && status == that.status && Objects.equals(bookedAt, that.bookedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, car, startDate, endDate, price, status, bookedAt);
    }

    @Override
    public String toString() {
        return "CarBooking{" +
            "id=" + id +
            ", user=" + user +
            ", car=" + car +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", price=" + price +
            ", status=" + status +
            ", bookedAt=" + bookedAt +
            '}';
    }
}
