package com.example.skeletune.data.model;

import java.io.Serializable;

public class NotaMania implements Serializable {

    private Integer idNotaMania;
    private Integer idChartMania;
    private Integer tiempoMs;
    private Byte carril;
    private Integer duracionMs;
    private Integer imagenMediaId;
    private Tipo tipo;

    public enum Tipo {
        NORMAL, LONG, SLIDE, GHOST
    }

    public NotaMania() {
    }

    public Integer getIdNotaMania() {
        return idNotaMania;
    }

    public void setIdNotaMania(Integer idNotaMania) {
        this.idNotaMania = idNotaMania;
    }

    public Integer getIdChartMania() {
        return idChartMania;
    }

    public void setIdChartMania(Integer idChartMania) {
        this.idChartMania = idChartMania;
    }

    public Integer getTiempoMs() {
        return tiempoMs;
    }

    public void setTiempoMs(Integer tiempoMs) {
        this.tiempoMs = tiempoMs;
    }

    public Byte getCarril() {
        return carril;
    }

    public void setCarril(Byte carril) {
        this.carril = carril;
    }

    public Integer getDuracionMs() {
        return duracionMs;
    }

    public void setDuracionMs(Integer duracionMs) {
        this.duracionMs = duracionMs;
    }

    public Integer getImagenMediaId() {
        return imagenMediaId;
    }

    public void setImagenMediaId(Integer imagenMediaId) {
        this.imagenMediaId = imagenMediaId;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}