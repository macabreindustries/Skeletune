package com.example.skeletune.data.model;

import java.io.Serializable;

public class VideoEducativo implements Serializable {
    private Integer idVideo;
    private Integer idProfesor;
    private String titulo;
    private String descripcion;
    private String urlVideo;
    private Integer idThumbnailMedia;
    private String fechaSubida;

    public VideoEducativo() {}

    // Getters y Setters...


    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public Integer getIdThumbnailMedia() {
        return idThumbnailMedia;
    }

    public void setIdThumbnailMedia(Integer idThumbnailMedia) {
        this.idThumbnailMedia = idThumbnailMedia;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}