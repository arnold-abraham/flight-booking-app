package com.example.flightbooking.service;

import com.example.flightbooking.dto.FlightDtos;
import com.example.flightbooking.model.Flight;
import com.example.flightbooking.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper mapper;

    public FlightService(FlightRepository flightRepository, ModelMapper mapper) {
        this.flightRepository = flightRepository;
        this.mapper = mapper;
    }

    @Transactional
    public FlightDtos.FlightResponse create(FlightDtos.CreateFlightRequest req) {
        // Manual mapping to avoid ModelMapper field-mapping issues with public DTO fields
        if (req.departureTime == null) {
            throw new IllegalArgumentException("departureTime is required");
        }
        if (req.arrivalTime == null) {
            throw new IllegalArgumentException("arrivalTime is required");
        }
        if (!req.arrivalTime.isAfter(req.departureTime)) {
            throw new IllegalArgumentException("arrivalTime must be after departureTime");
        }

        Flight f = new Flight();
        f.setFlightNumber(req.flightNumber);
        f.setSource(req.source);
        f.setDestination(req.destination);
        f.setDepartureTime(req.departureTime);
        f.setArrivalTime(req.arrivalTime);
        f.setPrice(req.price);
        f.setSeatsAvailable(req.seatsAvailable);

        flightRepository.save(f);
        return mapper.map(f, FlightDtos.FlightResponse.class);
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public List<Flight> search(String source, String destination, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        // Using repository query will compare DATE(departureTime) = DATE(?3)
        return flightRepository.search(source, destination, start);
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Flight not found"));
    }

    @Transactional
    public void reduceSeats(Flight flight, int seats) {
        if (flight.getSeatsAvailable() < seats) {
            throw new IllegalArgumentException("Not enough seats available");
        }
        flight.setSeatsAvailable(flight.getSeatsAvailable() - seats);
        flightRepository.save(flight);
    }

    @Transactional
    public void increaseSeats(Flight flight, int seats) {
        flight.setSeatsAvailable(flight.getSeatsAvailable() + seats);
        flightRepository.save(flight);
    }
}
