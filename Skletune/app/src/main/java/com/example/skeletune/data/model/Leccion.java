package com.example.skeletune.data.model;

public class Leccion {
    private Integer idLeccion;
    private String titulo;
    private String descripcion;
    private Tipo tipo;
    private Nivel nivel;
    private Integer idCancion;
    private Integer idVideo;

    public enum Tipo { TEORIA, PRACTICA, EJERCICIO }
    public enum Nivel { BASICO, INTERMEDIO, AVANZADO }

    public Leccion() {}

    // Getters y Setters
    public Integer getIdLeccion() { return idLeccion; }
    public void setIdLeccion(Integer idLeccion) { this.idLeccion = idLeccion; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public Nivel getNivel() { return nivel; }
    public void setNivel(Nivel nivel) { this.nivel = nivel; }
    public Integer getIdCancion() { return idCancion; }
    public void setIdCancion(Integer idCancion) { this.idCancion = idCancion; }
    public Integer getIdVideo() { return idVideo; }
    public void setIdVideo(Integer idVideo) { this.idVideo = idVideo; }
}