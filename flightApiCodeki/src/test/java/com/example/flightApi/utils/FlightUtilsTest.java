package com.example.flightApi.utils;

import com.example.flightApi.dto.FlightDTO;
import com.example.flightApi.model.Company;
import com.example.flightApi.model.Dolar;
import com.example.flightApi.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class FlightUtilsTest {

    @Autowired
    FlightUtils flightUtils; //debo inyectar la clase a testear


    @BeforeEach
    public void setUp() {

        //   List<Flight> flightList;
        // List<FlightDTO> flightDTOList;

    }

    @Test
    void flightMapperTest() {
        List<Flight> flightList = new ArrayList<>();
        List<FlightDTO> flightDTOList = new ArrayList<>();
        double dolarPrice = 1020;

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setOrigin("Cor");
        flight.setDestination("EZE");
        flight.setArrivalDateTime("algundia");
        flight.setDepartureDateTime("otrodia");
        flight.setFrequency("mensual");
        flight.setPrice(100);

        flightList.add(flight);

        flightDTOList = flightUtils.flightMapper(flightList, dolarPrice);

        FlightDTO flightDTO = flightDTOList.get(0);
        assertEquals(1, flightDTO.getId());
        assertEquals(102000, flightDTO.getConvertedPrice());
    }

    @Test
    void detectedOffers() {
        Company company1=new Company(1L,"AA","aerolineas");
        List<Flight> flightList = new ArrayList<>();
        Flight flight1 = new Flight(1L, "bue", "dubai", "18/04/2024", "19/04/2024", 253.55, "semanal",company1);
        Flight flight2 = new Flight(1L, "bue", "hong kong", "18/04/2024", "19/04/2024", 350, "semanal",company1);
        Flight flight3 = new Flight(1L, "bue", "miami", "18/04/2024", "19/04/2024", 200.55, "diario",company1);

        flightList.add(flight1);
        flightList.add(flight2);
        flightList.add(flight3);

        FlightUtils flightUtils = new FlightUtils();
        // aca abajo estamos llamando al metodo de utils , le pasamos la lista y el precio maximo de la oferta

        List<Flight> offers = flightUtils.detectedOffers(flightList, 254.00);

        assertEquals(2, offers.size());
        assertTrue(offers.contains(flight1));
        assertTrue(offers.contains(flight3));
        assertFalse(offers.contains(flight2));
    }

    @Test
    void fethDolarTest() {
        //generamos el contexto

        FlightUtils moockedFlighUtils = mock(FlightUtils.class);
        Dolar dolar = new Dolar("moneda", "casa", "tarjeta", 1000, 1200, LocalDateTime.of(2024, 4, 11, 15, 30));

        when(moockedFlighUtils.fetchDolar()).thenReturn(dolar);
        //llamamos a la funcionalidad para comparar con el anterior
        Dolar dolar2r = moockedFlighUtils.fetchDolar();
//verifico la salida
        assertEquals(dolar.getPromedio(),dolar2r.getPromedio());


    }


}