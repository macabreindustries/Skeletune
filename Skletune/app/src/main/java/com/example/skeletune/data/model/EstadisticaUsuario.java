package com.example.skeletune.data.model;

public class EstadisticaUsuario {
    private Integer idEstadistica;
    private Integer idUsuario;
    private String fechaActualizacion;
    private int totalMinutosPractica;
    private int leccionesCompletadas;
    private int cancionesAprendidas;
    private int rachaDias;
    private Nivel nivelGeneral;

    public enum Nivel {
        PRINCIPIANTE, INTERMEDIO, AVANZADO, MAESTRO
    }

    public EstadisticaUsuario() {}

    // Getters y Setters
    public Integer getIdEstadistica() { return idEstadistica; }
    public void setIdEstadistica(Integer idEstadistica) { this.idEstadistica = idEstadistica; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public int getTotalMinutosPractica() { return totalMinutosPractica; }
    public void setTotalMinutosPractica(int totalMinutosPractica) { this.totalMinutosPractica = totalMinutosPractica; }
    public int getLeccionesCompletadas() { return leccionesCompletadas; }
    public void setLeccionesCompletadas(int leccionesCompletadas) { this.leccionesCompletadas = leccionesCompletadas; }
    public int getCancionesAprendidas() { return cancionesAprendidas; }
    public void setCancionesAprendidas(int cancionesAprendidas) { this.cancionesAprendidas = cancionesAprendidas; }
    public int getRachaDias() { return rachaDias; }
    public void setRachaDias(int rachaDias) { this.rachaDias = rachaDias; }
    public Nivel getNivelGeneral() { return nivelGeneral; }
    public void setNivelGeneral(Nivel nivelGeneral) { this.nivelGeneral = nivelGeneral; }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}