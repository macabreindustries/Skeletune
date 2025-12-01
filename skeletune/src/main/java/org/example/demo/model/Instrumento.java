package org.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Instrumento")
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_instrumento;

    @Column(name = "nombre_instrumento", nullable = false, unique = true, length = 100)
    private String nombreInstrumento;

    @Column(length = 100)
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
