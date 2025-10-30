package com.example.flightbooking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public class BookingDtos {
    public static class CreateBookingRequest {
        @NotNull public Long flightId;
        @Min(1) public int seats = 1;
    }
    @Data
    public static class BookingResponse {
        public Long id;
        public Long userId;
        public Long flightId;
        public String status;
        public int seats;
        public java.math.BigDecimal totalPrice;
        public java.time.LocalDateTime bookingTime;
    }
}
