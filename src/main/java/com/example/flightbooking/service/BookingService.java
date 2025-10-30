package com.example.flightbooking.service;

import com.example.flightbooking.dto.BookingDtos;
import com.example.flightbooking.model.*;
import com.example.flightbooking.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final UserService userService;
    private final ModelMapper mapper;

    public BookingService(BookingRepository bookingRepository, FlightService flightService, UserService userService, ModelMapper mapper) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Transactional
    public BookingDtos.BookingResponse book(Long userId, BookingDtos.CreateBookingRequest req) {
        User user = userService.findById(userId);
        Flight flight = flightService.findById(req.flightId);

        flightService.reduceSeats(flight, req.seats);

        Booking b = new Booking();
        b.setUser(user);
        b.setFlight(flight);
        b.setSeats(req.seats);
        b.setStatus(BookingStatus.BOOKED);
        b.setTotalPrice(flight.getPrice().multiply(BigDecimal.valueOf(req.seats)));
        bookingRepository.save(b);

        BookingDtos.BookingResponse res = mapper.map(b, BookingDtos.BookingResponse.class);
        res.userId = user.getId();
        res.flightId = flight.getId();
        return res;
    }

    public List<Booking> findByUser(Long userId) {
        return bookingRepository.findByUser_Id(userId);
    }

    @Transactional
    public void cancel(Long bookingId, Long requesterUserId, boolean isAdmin) {
        Booking b = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if (!isAdmin && !b.getUser().getId().equals(requesterUserId)) {
            throw new SecurityException("Not authorized to cancel this booking");
        }
        if (b.getStatus() == BookingStatus.CANCELLED) return;
        b.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(b);
        flightService.increaseSeats(b.getFlight(), b.getSeats());
    }
}
