package com.example.skeletune.data.model;

public class UsuarioInstrumento {
    private int idUsuario;
    private int idInstrumento;
    private String nivel;

    public UsuarioInstrumento() {}

    // Getters y Setters...


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(int idInstrumento) {
        this.idInstrumento = idInstrumento;
    }
}