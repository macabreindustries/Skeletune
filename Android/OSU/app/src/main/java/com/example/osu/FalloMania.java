package com.example.osu;

import com.google.gson.annotations.SerializedName;

public class FalloMania {

    public enum Tipo {
        early, late, miss
    }

    @SerializedName("idFalloMania")
    private Integer idFalloMania;

    @SerializedName("idPartidaMania")
    private Integer idPartidaMania;

    @SerializedName("tiempoMs")
    private Integer tiempoMs;

    @SerializedName("tipo")
    private Tipo tipo;

    @SerializedName("desviacionMs")
    private Integer desviacionMs;

    // Getters
    public Integer getIdFalloMania() { return idFalloMania; }
    public Integer getIdPartidaMania() { return idPartidaMania; }
    public Integer getTiempoMs() { return tiempoMs; }
    public Tipo getTipo() { return tipo; }
    public Integer getDesviacionMs() { return desviacionMs; }
}