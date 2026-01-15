package com.example.skeletune.data.model;

public class LikePublicacion {
    private Integer idLike;
    private Integer idUsuario;
    private Integer idPublicacion;
    private String fechaLike;

    public LikePublicacion() {}

    public Integer getIdLike() { return idLike; }
    public void setIdLike(Integer idLike) { this.idLike = idLike; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(Integer idPublicacion) { this.idPublicacion = idPublicacion; }
    public String getFechaLike() { return fechaLike; }
    public void setFechaLike(String fechaLike) { this.fechaLike = fechaLike; }
}