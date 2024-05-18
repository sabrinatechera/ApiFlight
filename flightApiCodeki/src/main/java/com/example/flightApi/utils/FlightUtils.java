package com.example.flightApi.utils;

import com.example.flightApi.dto.FlightDTO;
import com.example.flightApi.model.Dolar;
import com.example.flightApi.model.Flight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightUtils {

    @Value("${dollar.card-url}")
    private String API_DOLLAR_CARD;

    public List<Flight> detectedOffers(List<Flight> flightList, double maxPrice) {
        return flightList.stream()
                .filter(flight -> flight.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // este mapper va a recibir una lista de vuelos y como tengo que devolver una lista de dto lo que debo hacer es
    //recorrer cada atributo de la lista dada y agregarlo en la de dto
    public List<FlightDTO> flightMapper(List<Flight> flightList, double price) {
        List<FlightDTO> flightDTOList = new ArrayList<>();
        for (Flight flight : flightList) {
            flightDTOList.add(new FlightDTO(flight.getId(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDateTime(),
                    flight.getArrivalDateTime(), flight.getPrice() * price, flight.getFrequency()));
        }
        return flightDTOList;
    }

    public FlightDTO mapToFlightDTO(Flight flight, double price) {
        return new FlightDTO(
                flight.getId(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureDateTime(),
                flight.getArrivalDateTime(),
                flight.getPrice() * price,
                flight.getFrequency()
        );
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // instanciacion del objeto restTemplate
    public Dolar fetchDolar() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_DOLLAR_CARD, Dolar.class);
    }

}
