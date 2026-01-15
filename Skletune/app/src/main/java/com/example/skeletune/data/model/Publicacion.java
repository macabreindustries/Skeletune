package com.example.skeletune.data.model;

import java.util.Set;

public class Publicacion {
    private Integer idPublicacion;
    private Integer idUsuario;
    private String texto;
    private String fechaPublicacion;
    private String idMediaPrincipal;
    private Set<Integer> mediaAdjuntosIds;

    public Publicacion() {}

    // Getters y Setters...


    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // Cambiamos Integer por String en el retorno
    public String getIdMediaPrincipal() {
        return idMediaPrincipal;
    }

    // Cambiamos Integer por String en el parámetro
    public void setIdMediaPrincipal(String idMediaPrincipal) {
        this.idMediaPrincipal = idMediaPrincipal;
    }
    public Set<Integer> getMediaAdjuntosIds() {
        return mediaAdjuntosIds;
    }

    public void setMediaAdjuntosIds(Set<Integer> mediaAdjuntosIds) {
        this.mediaAdjuntosIds = mediaAdjuntosIds;
    }
}