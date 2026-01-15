package com.example.skeletune.data.model;

public class Comentario {
    private Integer idComentario;
    private Integer idPublicacion;
    private Integer idUsuario;
    private String comentario;
    private String fechaComentario; // Recibido como String ISO-8601

    public Comentario() {}

    // Getters y Setters
    public Integer getIdComentario() { return idComentario; }
    public void setIdComentario(Integer idComentario) { this.idComentario = idComentario; }
    public Integer getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(Integer idPublicacion) { this.idPublicacion = idPublicacion; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public String getFechaComentario() { return fechaComentario; }
    public void setFechaComentario(String fechaComentario) { this.fechaComentario = fechaComentario; }
}