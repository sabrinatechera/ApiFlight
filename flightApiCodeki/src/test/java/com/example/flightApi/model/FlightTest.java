package com.example.flightApi.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class FlightTest {

    private static Flight flight;

    @Test
    public void setAndGetOriginTest() {
        String testedOrigin = "BUE";
        System.out.println("se le asigna el origen "+ testedOrigin );
        flight.setOrigin(testedOrigin);
        Assertions.assertEquals(flight.getOrigin(),testedOrigin);
    }

    @BeforeAll
    public static void setUp() {
        System.out.println("se esta creando el vuelo ");
        flight = new Flight();
    }



}