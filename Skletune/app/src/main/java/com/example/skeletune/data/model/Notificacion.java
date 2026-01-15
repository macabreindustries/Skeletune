package com.example.skeletune.data.model;

public class Notificacion {
    private Integer idNotificacion;
    private Integer idUsuario;
    private Tipo tipo;
    private String titulo;
    private String mensaje;
    private Integer idReferencia;
    private String tablaReferencia;
    private String fecha;
    private boolean leido;

    public enum Tipo { MENSAJE, LIKE, SEGUIDOR, LECCION, SISTEMA }

    public Notificacion() {}

    // Getters y Setters...


    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getTablaReferencia() {
        return tablaReferencia;
    }

    public void setTablaReferencia(String tablaReferencia) {
        this.tablaReferencia = tablaReferencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}