package com.example.flightApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
    private String departureDateTime;
    private String arrivalDateTime;
    private double price;// esta en usd
    private String frequency;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;


    public Flight(String origin, String destination, String departureDateTime, String arrivalDateTime, double price, String frequency) {
        this.origin = origin;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
        this.frequency = frequency;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}