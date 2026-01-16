package com.example.skeletune.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Racha {
    @SerializedName("rachaActual")
    private int rachaActual;

    @SerializedName("mejorRacha")
    private int mejorRacha;

    @SerializedName("totalDiasPracticados")
    private int totalDiasPracticados;

    @SerializedName("historialMes")
    private List<String> historialMes; // Lista de fechas en formato "yyyy-MM-dd" donde hubo actividad

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getMejorRacha() {
        return mejorRacha;
    }

    public void setMejorRacha(int mejorRacha) {
        this.mejorRacha = mejorRacha;
    }

    public int getTotalDiasPracticados() {
        return totalDiasPracticados;
    }

    public void setTotalDiasPracticados(int totalDiasPracticados) {
        this.totalDiasPracticados = totalDiasPracticados;
    }

    public List<String> getHistorialMes() {
        return historialMes;
    }

    public void setHistorialMes(List<String> historialMes) {
        this.historialMes = historialMes;
    }


    // Getters y Setters
}