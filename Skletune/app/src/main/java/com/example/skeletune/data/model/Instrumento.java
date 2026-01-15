package com.example.skeletune.data.model;

import com.google.gson.annotations.SerializedName;

public class Instrumento {
    @SerializedName("id_instrumento")
    private int idInstrumento;
    private String nombreInstrumento;
    private String tipo;

    public Instrumento() {}

    public int getIdInstrumento() { return idInstrumento; }
    public void setIdInstrumento(int idInstrumento) { this.idInstrumento = idInstrumento; }
    public String getNombreInstrumento() { return nombreInstrumento; }
    public void setNombreInstrumento(String nombreInstrumento) { this.nombreInstrumento = nombreInstrumento; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}