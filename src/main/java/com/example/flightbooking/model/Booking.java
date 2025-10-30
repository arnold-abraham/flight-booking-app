package com.example.flightbooking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private User user;

    @ManyToOne(optional=false)
    private Flight flight;

    @Column(nullable=false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private BookingStatus status = BookingStatus.BOOKED;

    @Column(nullable=false)
    private int seats;

    @Column(nullable=false)
    private BigDecimal totalPrice;
}
