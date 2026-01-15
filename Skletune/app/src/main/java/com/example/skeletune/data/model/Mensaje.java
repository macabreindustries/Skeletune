package com.example.skeletune.data.model;

public class Mensaje {
    private Integer idMensaje;
    private Integer idEmisor;
    private Integer idReceptor;
    private String mensaje;
    private Integer idMedia;
    private String fechaEnvio;
    private boolean visto;

    public Mensaje() {}

    // Getters y Setters
    public Integer getIdMensaje() { return idMensaje; }
    public void setIdMensaje(Integer idMensaje) { this.idMensaje = idMensaje; }
    public Integer getIdEmisor() { return idEmisor; }
    public void setIdEmisor(Integer idEmisor) { this.idEmisor = idEmisor; }
    public Integer getIdReceptor() { return idReceptor; }
    public void setIdReceptor(Integer idReceptor) { this.idReceptor = idReceptor; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Integer getIdMedia() { return idMedia; }
    public void setIdMedia(Integer idMedia) { this.idMedia = idMedia; }
    public String getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(String fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public boolean isVisto() { return visto; }
    public void setVisto(boolean visto) { this.visto = visto; }
}