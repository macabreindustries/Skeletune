package com.example.skeletune.data.model;

public class Historia {
    private Integer idHistoria;
    private Integer idUsuario;
    private Integer idMedia;
    private String fechaPublicacion;
    private String expiraEn;

    public Historia() {}

    // Getters y Setters
    public Integer getIdHistoria() { return idHistoria; }
    public void setIdHistoria(Integer idHistoria) { this.idHistoria = idHistoria; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdMedia() { return idMedia; }
    public void setIdMedia(Integer idMedia) { this.idMedia = idMedia; }
    public String getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(String fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public String getExpiraEn() { return expiraEn; }
    public void setExpiraEn(String expiraEn) { this.expiraEn = expiraEn; }
}