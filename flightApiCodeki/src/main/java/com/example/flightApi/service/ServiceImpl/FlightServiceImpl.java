package com.example.flightApi.service.ServiceImpl;

import com.example.flightApi.dto.FlightDTO;
import com.example.flightApi.exception.ResourceNotFoundException;
import com.example.flightApi.model.Company;
import com.example.flightApi.model.Flight;
import com.example.flightApi.repository.CompanyRepository;
import com.example.flightApi.repository.FlightRepository;
import com.example.flightApi.service.FlightService;
import com.example.flightApi.utils.FlightUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final CompanyRepository companyRepository;
    private final FlightUtils flightUtils;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, CompanyRepository companyRepository, FlightUtils flightUtils) {
        this.flightRepository = flightRepository;
        this.companyRepository = companyRepository;
        this.flightUtils = flightUtils;
    }

    @Override
    public Flight createFlight(Flight flight) {
        Flight newFlight = new Flight();
        newFlight.setOrigin(flight.getOrigin());
        newFlight.setDestination(flight.getDestination());
        newFlight.setDepartureDateTime(flight.getDepartureDateTime());
        newFlight.setArrivalDateTime(flight.getArrivalDateTime());
        newFlight.setPrice(flight.getPrice());
        newFlight.setFrequency(flight.getFrequency());

        if (flight.getCompany() != null) {
            Optional<Company> optionalCompany = companyRepository.findById(flight.getCompany().getId());
            if (optionalCompany.isPresent()) {
                newFlight.setCompany(optionalCompany.get());
            } else {
                throw new ResourceNotFoundException("La compañía no existe", "id", flight.getCompany().getId());
            }
        }

        return flightRepository.save(newFlight);
    }

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightDTO.getId());
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            return flightUtils.mapToFlightDTO(flight, getDollar());
        } else {
            throw new ResourceNotFoundException("Flight", "id", flightDTO.getId());
        }
    }

    @Override
    public List<FlightDTO> findAllFlights() {
        List<Flight> allObjFlight = flightRepository.findAll();
        return flightUtils.flightMapper(allObjFlight, getDollar());
    }


    @Override
    public Optional<Flight> findById(Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            return flight;
        } else {
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }


    @Override
    public Optional<FlightDTO> findFlightDTOById(Long id) {
        return flightRepository.findById(id)
                .map(flight -> flightUtils.mapToFlightDTO(flight, getDollar()));
    }


    @Override
    public Optional<Flight> updateFlight(@RequestBody Flight flight) {
        flightRepository.save(flight);
        return flightRepository.findById(flight.getId());
    }


    @Override
    public List<Flight> findByOrigin(String origin) {
        return flightRepository.findByOrigin(origin);
    }

    @Override
    public List<Flight> findByOriginAndDestiny(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    @Override
    public List<Flight> findByPrice(double price) {
        List<Flight> allFlights = flightRepository.findAll();
        return flightUtils.detectedOffers(allFlights, price);
    }

    private double getDollar() {
        return flightUtils.fetchDolar().getPromedio();
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            flightRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Flight", "id", id);
        }
    }

}
