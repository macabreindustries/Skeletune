package com.example.skeletune.data.model;

public class Seguidor {
    private Integer idSeguidor;
    private Integer idSeguido;
    private String fechaSeguimiento;

    public Seguidor() {}

    // Getters y Setters...


    public Integer getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(Integer idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public Integer getIdSeguido() {
        return idSeguido;
    }

    public void setIdSeguido(Integer idSeguido) {
        this.idSeguido = idSeguido;
    }

    public String getFechaSeguimiento() {
        return fechaSeguimiento;
    }

    public void setFechaSeguimiento(String fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }
}