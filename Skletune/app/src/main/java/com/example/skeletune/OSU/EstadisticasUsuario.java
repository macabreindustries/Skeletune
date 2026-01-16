package com.example.skeletune.OSU;

import java.math.BigDecimal;public class EstadisticasUsuario {

    private int totalPartidas;
    private int puntajeMaximo;
    private BigDecimal promedioAccuracy;
    private int totalPerfects;
    private int totalGreats;

    // Getters (los setters no son necesarios si solo deserializas con Gson)

    public int getTotalPartidas() {
        return totalPartidas;
    }

    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public BigDecimal getPromedioAccuracy() {
        return promedioAccuracy;
    }

    public int getTotalPerfects() {
        return totalPerfects;
    }

    public int getTotalGreats() {
        return totalGreats;
    }
}