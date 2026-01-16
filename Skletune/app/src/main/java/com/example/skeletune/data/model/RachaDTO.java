package com.example.skeletune.data.model;

import java.util.List;
import java.util.Map;

public class RachaDTO {
    private int rachaActual;
    private int mejorRacha;
    private int totalDiasPracticados;
    private Map<String, Boolean> estadoSemana;
    private List<String> historialMes;

    // Getters
    public int getRachaActual() { return rachaActual; }
    public int getMejorRacha() { return mejorRacha; }
    public int getTotalDiasPracticados() { return totalDiasPracticados; }
    public Map<String, Boolean> getEstadoSemana() { return estadoSemana; }
    public List<String> getHistorialMes() { return historialMes; }
}
