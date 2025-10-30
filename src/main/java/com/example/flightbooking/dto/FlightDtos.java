package com.example.flightbooking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class FlightDtos {
    public static class CreateFlightRequest {
        @NotBlank public String flightNumber;
        @NotBlank public String source;
        @NotBlank public String destination;
        @NotNull @Future public LocalDateTime departureTime;
        @NotNull @Future public LocalDateTime arrivalTime;
        @NotNull @Min(0) public BigDecimal price;
        @Min(0) public int seatsAvailable;
    }
    @Data
    public static class FlightResponse {
        public Long id;
        public String flightNumber;
        public String source;
        public String destination;
        public LocalDateTime departureTime;
        public LocalDateTime arrivalTime;
        public BigDecimal price;
        public int seatsAvailable;
    }
}
