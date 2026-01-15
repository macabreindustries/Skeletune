package com.example.skeletune.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Cancion implements Serializable {

    @SerializedName("idCancion")
    private Integer idCancion;

    // AGREGAMOS LAS ETIQUETAS AQUÍ TAMBIÉN PARA SEGURIDAD
    @SerializedName("likesCount")
    private Integer likesCount = 0;

    @SerializedName("viewsCount")
    private Integer viewsCount = 0;

    @SerializedName("swipesCount")
    private Integer swipesCount = 0;
    @SerializedName("titulo")
    private String titulo;

    @SerializedName("artista")
    private String artista;

    @SerializedName("dificultad")
    private String dificultad; // Usamos String para que sea más flexible al recibir del servidor

    @SerializedName("urlAudio")
    private String urlAudio;

    @SerializedName("urlPartitura")
    private String urlPartitura;

    @SerializedName("imagenUrl")
    private String imagenUrl;

    @SerializedName("idAdmin")
    private Integer idAdmin;


    // Constructor vacío para GSON
    public Cancion() {}

    // GETTERS Y SETTERS
    public Integer getIdCancion() { return idCancion; }
    public void setIdCancion(Integer idCancion) { this.idCancion = idCancion; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }

    public String getUrlAudio() { return urlAudio; }
    public void setUrlAudio(String urlAudio) { this.urlAudio = urlAudio; }

    public String getUrlPartitura() { return urlPartitura; }
    public void setUrlPartitura(String urlPartitura) { this.urlPartitura = urlPartitura; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Integer getIdAdmin() { return idAdmin; }
    public void setIdAdmin(Integer idAdmin) { this.idAdmin = idAdmin; }

    public Integer getLikesCount() { return likesCount; }
    public void setLikesCount(Integer likesCount) { this.likesCount = likesCount; }

    public Integer getViewsCount() { return viewsCount; }
    public void setViewsCount(Integer viewsCount) { this.viewsCount = viewsCount; }

    public Integer getSwipesCount() { return swipesCount; }
    public void setSwipesCount(Integer swipesCount) { this.swipesCount = swipesCount; }
}