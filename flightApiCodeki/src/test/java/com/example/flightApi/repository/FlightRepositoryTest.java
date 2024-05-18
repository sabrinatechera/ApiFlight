package com.example.flightApi.repository;

import com.example.flightApi.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class FlightRepositoryTest {


    @Autowired
    private FlightRepository flightRepository;// inyecto el repo y la clase flight

    private Flight flight;


    @BeforeEach // se crea un vuelo antes de testear y crea el contexto
    void setUp() {
        flight = new Flight("bue", "dubai", "18/04/2024", "19/04/2024", 253.55, "semanal");
    }


    @Test
    void saveFlightTest() {
//para testear guardo un vuelo, y vero que eso que guarde no sea nulo y que si me traigo el id de eso guardado sea>0
        Flight flightBD = flightRepository.save(flight);

        assertThat(flightBD).isNotNull();
        assertThat(flightBD.getId()).isGreaterThan(0);
    }

    @Test
    void flightFindByIdTest() {
// guardo un vuelo y luego lo busco por el id, para eso obtengo ese id que guarde. luego veo que no venga nulo
        flightRepository.save(flight);
        Flight flightBD = flightRepository.findById(flight.getId()).get();

        assertThat(flightBD).isNotNull();
    }
    @BeforeEach
    void cleanDatabase() {
        flightRepository.deleteAll(); // Eliminar todos los vuelos antes de cada prueba
    }
    @Test
    void flightFindAllTest() {
        // Guardar dos vuelos en la base de datos
        Flight flight1 = new Flight("BUE", "MAD", "10:00", "13:00", 300.0, "Diario");
        Flight flight2 = new Flight("MAD", "COR", "8:00", "11:00", 200.0, "Diario");
        flightRepository.save(flight1);
        flightRepository.save(flight2);

        // Buscar todos los vuelos
        List<Flight> flightList = flightRepository.findAll();

        // Verificar que la lista no sea nula y tenga el tamaño correcto
        assertThat(flightList).isNotNull();
        assertThat(flightList.size()).isEqualTo(2); // Debe ser igual a 2 ya que solo hemos agregado dos vuelos
    }
    @Test
    void flightDeleteById(){
        //configuracion previa
        flightRepository.save(flight);
        //llamar la funcionalidad
        flightRepository.deleteById(flight.getId());

        // verificar la salidad o el comportamiento
        Optional<Flight> deletedFlight = flightRepository.findById(flight.getId());
        assertThat(deletedFlight).isEmpty();// si esta vacio quiere decir que se borro bien el id
    }

    @Test
    void flightUpdateTest(){
        // guardo el vuelo y lo busco por if
        flightRepository.save(flight);
        Flight flightBD = flightRepository.findById(flight.getId()).get();

        // le seteo estos nuevos atributos
        flightBD.setOrigin("BRA");
        flightBD.setDestination("ARG");

        // guardo en una variable ese vuelo actualizado
        Flight flightUpdated = flightRepository.save(flightBD);

        //verifico que esa varible actualizada conincida con lo que actualizace anteriormente
        assertThat(flightUpdated.getOrigin()).isEqualTo("BRA");
        assertThat(flightUpdated.getDestination()).isEqualTo("ARG");
    }

    @Test
    void findByOriginTest() {
        // Guardar algunos vuelos en la base de datos
        Flight flight1 = new Flight("MAD", "COR", "8.00", "11.00", 200.0, "Diaria");
        Flight flight2 = new Flight("MAD", "MIA", "10.00", "14.00", 300.0, "Diaria");
        flightRepository.save(flight1);
        flightRepository.save(flight2);

        // Buscar vuelos por origen
        List<Flight> flightsByOrigin = flightRepository.findByOrigin("MAD");

        // Verificar que se hayan encontrado los vuelos correctos
        assertThat(flightsByOrigin).isNotNull();
        assertThat(flightsByOrigin.size()).isEqualTo(2);
    }

    @Test
    void findByOriginAndDestinationTest() {
        // Guardar algunos vuelos en la base de datos
        Flight flight1 = new Flight("MAD", "COR", "8.00", "11.00", 200.0, "Diaria");
        Flight flight2 = new Flight("MAD", "MIA", "10.00", "14.00", 300.0, "Diaria");
        flightRepository.save(flight1);
        flightRepository.save(flight2);

        // Buscar vuelos por origen y  y despues corroboramos que devuelva lo correcto
        List<Flight> flightsByOriginAndDestination = flightRepository.findByOriginAndDestination("MAD", "COR");

        assertThat(flightsByOriginAndDestination).isNotNull();
        assertThat(flightsByOriginAndDestination.size()).isEqualTo(1);
        assertThat(flightsByOriginAndDestination.get(0).getDestination()).isEqualTo("COR");
    }


}