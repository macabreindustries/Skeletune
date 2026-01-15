package com.example.skeletune.data.model;

import com.google.gson.annotations.SerializedName;

public class ValidacionRol {
    @SerializedName("id_validacion")
    private int idValidacion;
    @SerializedName("id_usuario_validado")
    private int idUsuarioValidado;
    @SerializedName("id_admin_validador")
    private int idAdminValidador;
    private String fechaValidacion;
    private String estado;

    public ValidacionRol() {}

    // Getters y Setters...


    public int getIdValidacion() {
        return idValidacion;
    }

    public void setIdValidacion(int idValidacion) {
        this.idValidacion = idValidacion;
    }

    public int getIdUsuarioValidado() {
        return idUsuarioValidado;
    }

    public void setIdUsuarioValidado(int idUsuarioValidado) {
        this.idUsuarioValidado = idUsuarioValidado;
    }

    public int getIdAdminValidador() {
        return idAdminValidador;
    }

    public void setIdAdminValidador(int idAdminValidador) {
        this.idAdminValidador = idAdminValidador;
    }

    public String getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(String fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}