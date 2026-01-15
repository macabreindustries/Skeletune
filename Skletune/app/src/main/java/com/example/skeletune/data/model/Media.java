package com.example.skeletune.data.model;

public class Media {
    private Integer idMedia;
    private Integer idUsuario;
    private Tipo tipo;
    private String urlArchivo;
    private String fechaSubida;
    private String descripcion;

    public enum Tipo { IMAGEN, VIDEO, AUDIO }

    public Media() {}

    // Getters y Setters
    public Integer getIdMedia() { return idMedia; }
    public void setIdMedia(Integer idMedia) { this.idMedia = idMedia; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String urlArchivo) { this.urlArchivo = urlArchivo; }
    public String getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(String fechaSubida) { this.fechaSubida = fechaSubida; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}