package com.example.skeletune.data.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class Mensaje {

    @SerializedName("idMensaje")
    private Integer idMensaje;

    @SerializedName("idEmisor")
    private Integer idEmisor;

    @SerializedName("idReceptor")
    private Integer idReceptor;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("idMedia")
    private Integer idMedia;

    @SerializedName("fechaEnvio")
    private LocalDateTime fechaEnvio;

    @SerializedName("visto")
    private boolean visto;

    // --- Getters ---
    public Integer getIdMensaje() { return idMensaje; }
    public Integer getIdEmisor() { return idEmisor; }
    public Integer getIdReceptor() { return idReceptor; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public boolean isVisto() { return visto; }

    // --- Setters (pueden ser útiles) ---
    public void setVisto(boolean visto) { this.visto = visto; }
}