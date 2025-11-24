package com.example.osu;

import com.google.gson.annotations.SerializedName;

public class Cancion {

    public enum Dificultad {
        FACIL,
        INTERMEDIO,
        DIFICIL
    }

    @SerializedName("idCancion")
    private Integer idCancion;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("artista")
    private String artista;

    @SerializedName("dificultad")
    private Dificultad dificultad;

    @SerializedName("urlAudio")
    private String urlAudio;

    @SerializedName("urlPartitura")
    private String urlPartitura;

    // --- CAMPO AÑADIDO ---
    @SerializedName("imagenUrl")
    private String imagenUrl;

    @SerializedName("idAdmin")
    private Integer idAdmin;

    // Getters y Setters
    public Integer getIdCancion() { return idCancion; }
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public Dificultad getDificultad() { return dificultad; }
    public String getUrlAudio() { return urlAudio; }
    public String getUrlPartitura() { return urlPartitura; }
    
    // --- GETTER AÑADIDO ---
    public String getImagenUrl() { return imagenUrl; }
    
    public Integer getIdAdmin() { return idAdmin; }
}