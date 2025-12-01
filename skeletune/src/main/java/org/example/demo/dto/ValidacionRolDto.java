package org.example.demo.dto;

import java.util.Date;

public class ValidacionRolDto {
    private int id_validacion;
    private int id_usuario_validado;
    private int id_admin_validador;
    private Date fechaValidacion;
    private String estado;

    // Getters and Setters
    public int getId_validacion() {
        return id_validacion;
    }

    public void setId_validacion(int id_validacion) {
        this.id_validacion = id_validacion;
    }

    public int getId_usuario_validado() {
        return id_usuario_validado;
    }

    public void setId_usuario_validado(int id_usuario_validado) {
        this.id_usuario_validado = id_usuario_validado;
    }

    public int getId_admin_validador() {
        return id_admin_validador;
    }

    public void setId_admin_validador(int id_admin_validador) {
        this.id_admin_validador = id_admin_validador;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
