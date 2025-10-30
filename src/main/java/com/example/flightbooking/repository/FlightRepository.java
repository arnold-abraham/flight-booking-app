package com.example.flightbooking.repository;

import com.example.flightbooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE lower(f.source)=lower(?1) AND lower(f.destination)=lower(?2) AND DATE(f.departureTime)=DATE(?3)")
    List<Flight> search(String source, String destination, LocalDateTime date);
}
