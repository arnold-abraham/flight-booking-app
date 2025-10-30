package com.example.flightbooking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "flights")
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String flightNumber;

    @Column(nullable=false)
    private String source;

    @Column(nullable=false)
    private String destination;

    @Column(nullable=false)
    private LocalDateTime departureTime;

    @Column(nullable=false)
    private LocalDateTime arrivalTime;

    @Column(nullable=false)
    private BigDecimal price;

    @Column(nullable=false)
    private int seatsAvailable;
}
