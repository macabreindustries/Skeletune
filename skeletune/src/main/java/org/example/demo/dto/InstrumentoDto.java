package org.example.demo.dto;

public class InstrumentoDto {
    private int id_instrumento;
    private String nombreInstrumento;
    private String tipo;

    // Getters and Setters
    public int getId_instrumento() {
        return id_instrumento;
    }

    public void setId_instrumento(int id_instrumento) {
        this.id_instrumento = id_instrumento;
    }

    public String getNombreInstrumento() {
        return nombreInstrumento;
    }

    public void setNombreInstrumento(String nombreInstrumento) {
        this.nombreInstrumento = nombreInstrumento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
