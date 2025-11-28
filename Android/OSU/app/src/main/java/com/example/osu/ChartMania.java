package com.example.osu;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ChartMania implements Serializable {

    public enum Dificultad implements Serializable {
        facil, media, dificil, experto
    }

    @SerializedName("idChartMania")
    private Integer idChartMania;

    @SerializedName("idCancion")
    private Integer idCancion;

    @SerializedName("dificultad")
    private Dificultad dificultad;

    @SerializedName("speedMultiplier")
    private Float speedMultiplier;

    @SerializedName("numPistas")
    private Byte numPistas;

    @SerializedName("createdBy")
    private Integer createdBy;

    @SerializedName("fechaCreacion")
    private String fechaCreacion; // LocalDateTime as String

    private List<NotaMania> notas;

    // Getters
    public Integer getIdChartMania() { return idChartMania; }
    public Integer getIdCancion() { return idCancion; }
    public Dificultad getDificultad() { return dificultad; }
    public Float getSpeedMultiplier() { return speedMultiplier; }
    public Byte getNumPistas() { return numPistas; }
    public Integer getCreatedBy() { return createdBy; }
    public String getFechaCreacion() { return fechaCreacion; }
    public List<NotaMania> getNotas() { return notas; }

    public void setNotas(List<NotaMania> notas) {
        this.notas = notas;
    }
}