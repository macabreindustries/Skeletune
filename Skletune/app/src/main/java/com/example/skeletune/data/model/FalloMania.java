package com.example.skeletune.data.model;

import java.io.Serializable;

public class FalloMania implements Serializable {

    private Integer idFalloMania;
    private Integer idPartidaMania;
    private Integer tiempoMs;
    private Tipo tipo;
    private Integer desviacionMs;

    public enum Tipo {
        MISS, LATE, EARLY, WRONG_KEY
    }

    public FalloMania() {
    }

    public Integer getIdFalloMania() {
        return idFalloMania;
    }

    public void setIdFalloMania(Integer idFalloMania) {
        this.idFalloMania = idFalloMania;
    }

    public Integer getIdPartidaMania() {
        return idPartidaMania;
    }

    public void setIdPartidaMania(Integer idPartidaMania) {
        this.idPartidaMania = idPartidaMania;
    }

    public Integer getTiempoMs() {
        return tiempoMs;
    }

    public void setTiempoMs(Integer tiempoMs) {
        this.tiempoMs = tiempoMs;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getDesviacionMs() {
        return desviacionMs;
    }

    public void setDesviacionMs(Integer desviacionMs) {
        this.desviacionMs = desviacionMs;
    }
}