package com.example.skeletune.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartidaMania implements Serializable {

    private Integer idPartidaMania;
    private Integer idUsuario;
    private Integer idChartMania;
    private String fecha;
    private Integer puntaje;
    private Double accuracy; // Usamos Double para facilitar el manejo en la UI
    private Integer comboMax;
    private Integer perfects;
    private Integer greats;
    private Integer goods;
    private Integer misses;
    private String detalles;

    public PartidaMania() {
    }

    public Integer getIdPartidaMania() {
        return idPartidaMania;
    }

    public void setIdPartidaMania(Integer idPartidaMania) {
        this.idPartidaMania = idPartidaMania;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdChartMania() {
        return idChartMania;
    }

    public void setIdChartMania(Integer idChartMania) {
        this.idChartMania = idChartMania;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getComboMax() {
        return comboMax;
    }

    public void setComboMax(Integer comboMax) {
        this.comboMax = comboMax;
    }

    public Integer getPerfects() {
        return perfects;
    }

    public void setPerfects(Integer perfects) {
        this.perfects = perfects;
    }

    public Integer getGreats() {
        return greats;
    }

    public void setGreats(Integer greats) {
        this.greats = greats;
    }

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
    }

    public Integer getMisses() {
        return misses;
    }

    public void setMisses(Integer misses) {
        this.misses = misses;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}