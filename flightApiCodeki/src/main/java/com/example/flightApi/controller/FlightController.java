package com.example.flightApi.controller;

import com.example.flightApi.dto.FlightDTO;
import com.example.flightApi.exception.ResourceNotFoundException;
import com.example.flightApi.model.Flight;
import com.example.flightApi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("")
    public List<FlightDTO> getAllFlights() {

        return flightService.findAllFlights();
    }

    @PostMapping("/agregar")
    public void createFlight(@RequestBody Flight flight) {
        flightService.createFlight(flight);
    }

    @GetMapping("/dto/{idflight}")
    public Optional <FlightDTO> findFlightById(@PathVariable Long idflight) {
        return flightService.findFlightDTOById(idflight);
    }

    @GetMapping("/{id}")
    public Optional<Flight> getFlightById(@PathVariable("id") Long id) {
        return flightService.findById(id);
    }


    @DeleteMapping("/eliminar/{id}")
    public String deleteFlight(@PathVariable Long id) {
        try {
            flightService.deleteById(id);
            return "el vuelo fue borrado correctamente";
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return "no se encontro el vuelo";
        }
    }

    @PutMapping("actualizar")
    public Flight updateFlight(@RequestBody Flight flight) {
        Optional<Flight> updatedFlight = flightService.updateFlight(flight);
        return updatedFlight.orElse(null);
    }

    @GetMapping("origin")
    public List<Flight> getByLocation(@RequestParam String origin) {
        return flightService.findByOrigin(origin);
    }

    @GetMapping("location")
    public List<Flight> getByLocationAndDestiny(@RequestParam String origin, @RequestParam String destination) {
        return flightService.findByOriginAndDestiny(origin, destination);
    }

    @GetMapping("/oferta")
    public List<Flight> findByPrice(@RequestParam double price) {
        return flightService.findByPrice(price);
    }


}
