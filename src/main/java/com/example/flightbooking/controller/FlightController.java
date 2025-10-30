package com.example.flightbooking.controller;

import com.example.flightbooking.dto.FlightDtos;
import com.example.flightbooking.model.Flight;
import com.example.flightbooking.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final ModelMapper mapper;

    public FlightController(FlightService flightService, ModelMapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @Operation(summary = "Add new flight (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FlightDtos.FlightResponse> create(@Valid @RequestBody FlightDtos.CreateFlightRequest req) {
        return ResponseEntity.ok(flightService.create(req));
    }

    @Operation(summary = "View all flights")
    @GetMapping()
    public ResponseEntity<List<FlightDtos.FlightResponse>> all() {
        List<FlightDtos.FlightResponse> list = flightService.findAll().stream()
                .map(f -> mapper.map(f, FlightDtos.FlightResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Search flights by source, destination, date (yyyy-MM-dd)")
    @GetMapping("/search")
    public ResponseEntity<List<FlightDtos.FlightResponse>> search(@RequestParam String source,
                                                                  @RequestParam String destination,
                                                                  @RequestParam String date) {
        LocalDate d = LocalDate.parse(date);
        List<FlightDtos.FlightResponse> list = flightService.search(source, destination, d).stream()
                .map(f -> mapper.map(f, FlightDtos.FlightResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
