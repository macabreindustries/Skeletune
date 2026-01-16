package com.example.skeletune.data.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class Notificacion {

    // El enum 'Tipo' debe coincidir con el del backend
    public enum Tipo {
        NUEVO_SEGUIDOR,
        LIKE_PUBLICACION,
        COMENTARIO_PUBLICACION,
        MENSAJE_NUEVO
    }

    @SerializedName("idNotificacion")
    private Integer idNotificacion;

    @SerializedName("idUsuario")
    private Integer idUsuario;

    @SerializedName("tipo")
    private Tipo tipo;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("idReferencia")
    private Integer idReferencia;

    @SerializedName("tablaReferencia")
    private String tablaReferencia;

    @SerializedName("fecha")
    private LocalDateTime fecha;

    @SerializedName("leido")
    private boolean leido;

    // --- Getters ---
    public Integer getIdNotificacion() { return idNotificacion; }
    public Integer getIdUsuario() { return idUsuario; }
    public Tipo getTipo() { return tipo; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFecha() { return fecha; }
    public boolean isLeido() { return leido; }
}