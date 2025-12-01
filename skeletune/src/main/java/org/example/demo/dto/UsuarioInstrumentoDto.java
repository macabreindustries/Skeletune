package org.example.demo.dto;

public class UsuarioInstrumentoDto {
    private int idUsuario;
    private int idInstrumento;
    private String nivel;

    // Getters and Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(int idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
