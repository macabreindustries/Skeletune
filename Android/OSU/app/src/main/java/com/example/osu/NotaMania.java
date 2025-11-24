package com.example.osu;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class NotaMania implements Serializable {

    public enum Tipo implements Serializable {
        normal, hold, flick, rest
    }

    @SerializedName("idNotaMania")
    private Integer idNotaMania;

    @SerializedName("idChartMania")
    private Integer idChartMania;

    @SerializedName("tiempoMs")
    private Integer tiempoMs;

    @SerializedName("carril")
    private Byte carril;

    @SerializedName("duracionMs")
    private Integer duracionMs;

    @SerializedName("imagenMediaId")
    private Integer imagenMediaId;

    @SerializedName("tipo")
    private Tipo tipo;

    // Getters
    public Integer getIdNotaMania() { return idNotaMania; }
    public Integer getIdChartMania() { return idChartMania; }
    public Integer getTiempoMs() { return tiempoMs; }
    public Byte getCarril() { return carril; }
    public Integer getDuracionMs() { return duracionMs; }
    public Integer getImagenMediaId() { return imagenMediaId; }
    public Tipo getTipo() { return tipo; }
}