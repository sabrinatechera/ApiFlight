package com.example.flightApi.service;

import com.example.flightApi.dto.FlightDTO;
import com.example.flightApi.exception.ResourceNotFoundException;
import com.example.flightApi.model.Flight;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface FlightService {


    Flight createFlight(Flight flight);

    FlightDTO createFlight(FlightDTO flightDTO);

    List<FlightDTO>findAllFlights();


    Optional<Flight> findById(Long id);

    Optional<FlightDTO> findFlightDTOById(Long id);

    Optional<Flight> updateFlight(@RequestBody Flight flight);

    List<Flight> findByOrigin(String origin);

    List<Flight> findByOriginAndDestiny(String origin, String destination);

    List<Flight> findByPrice(double price);

    void deleteById(Long id) throws ResourceNotFoundException;
}
