package com.example.flightApi.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dolar {

    private String moneda;
    private String casa;
    private String nombre;
    private double compra;
    private double venta;
    private LocalDateTime fechaActualizacion;


    public double getPromedio(){
        return ((getCompra()+getVenta()) / 2);
    }

}
