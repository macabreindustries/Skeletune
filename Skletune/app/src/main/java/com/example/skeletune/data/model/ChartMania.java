package com.example.skeletune.data.model;

import java.io.Serializable;
import java.util.List;

public class ChartMania implements Serializable {

    private Integer idChartMania;
    private Integer idCancion;
    private Dificultad dificultad;
    private Float speedMultiplier;
    private Byte numPistas;
    private Integer createdBy;
    private String fechaCreacion;
    private List<NotaMania> notas; // Relación con el listado de notas

    public enum Dificultad {
        EASY, NORMAL, HARD, EXPERT
    }

    public ChartMania() {
    }

    public Integer getIdChartMania() {
        return idChartMania;
    }

    public void setIdChartMania(Integer idChartMania) {
        this.idChartMania = idChartMania;
    }

    public Integer getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(Float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public Byte getNumPistas() {
        return numPistas;
    }

    public void setNumPistas(Byte numPistas) {
        this.numPistas = numPistas;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<NotaMania> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaMania> notas) {
        this.notas = notas;
    }
}