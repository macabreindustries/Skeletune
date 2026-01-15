package com.example.skeletune.data.model;

import java.io.Serializable;

public class Progreso implements Serializable {
    private Integer idProgreso;
    private Integer idUsuario;
    private Integer idLeccion;
    private String fecha;
    private Integer duracionMinutos;
    private String comentario;

    public Progreso() {}

    // Getters y Setters...


    public Integer getIdProgreso() {
        return idProgreso;
    }

    public void setIdProgreso(Integer idProgreso) {
        this.idProgreso = idProgreso;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdLeccion() {
        return idLeccion;
    }

    public void setIdLeccion(Integer idLeccion) {
        this.idLeccion = idLeccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}